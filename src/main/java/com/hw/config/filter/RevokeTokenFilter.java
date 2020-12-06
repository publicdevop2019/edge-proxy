package com.hw.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.revoke_token.AppRevokeTokenApplicationService;
import com.hw.aggregate.revoke_token.representation.AppRevokeTokenCardRep;
import com.hw.shared.IllegalJwtException;
import com.hw.shared.sql.SumPagedRep;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.hw.aggregate.revoke_token.model.RevokeToken.ENTITY_ISSUE_AT;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
@Component
public class RevokeTokenFilter extends ZuulFilter {

    @Autowired
    private AppRevokeTokenApplicationService revokeTokenRepo;

    @Autowired
    private ObjectMapper mapper;

    public static final String EDGE_PROXY_TOKEN_REVOKED = "token check failed";

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 2; // run before PreDecoration
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        long startTime = System.currentTimeMillis();
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
        String requestURI = httpServletRequestWrapper.getRequestURI();
        String authHeader = httpServletRequestWrapper.getHeader("authorization");
        /**
         * block both access token and refresh token
         */
        if ((authHeader != null && authHeader.contains("Bearer")) ||
                (requestURI.contains("/oauth/token") && httpServletRequestWrapper.getRequest().getParameter("refresh_token") != null)
        ) {
            Jwt jwt;
            try {
                if (authHeader != null && authHeader.contains("Bearer")) {
                    jwt = JwtHelper.decode(authHeader.replace("Bearer ", ""));
                } else {
                    jwt = JwtHelper.decode(httpServletRequestWrapper.getRequest().getParameter("refresh_token"));
                }
            } catch (IllegalArgumentException ex) {
                throw new ZuulException("not authorized", 401, "not authorized");
            }
            Map<String, Object> claims;
            try {
                claims = mapper.readValue(jwt.getClaims(), Map.class);

                Integer iat = (Integer) claims.get("iat");
                String userId = (String) claims.get("uid");
                String clientId = (String) claims.get("client_id");
                if (userId != null) {
                    checkToken(Long.parseLong(userId), ctx, iat);
                }
                if (clientId != null) {
                    checkToken(Long.parseLong(clientId), ctx, iat);
                }
                log.info("elapse in token filter::" + (System.currentTimeMillis() - startTime));
            } catch (IOException e) {
                // this block is left blank on purpose*/
            }

        }
        return null;
    }

    private void checkToken(Long id, RequestContext ctx, Integer iat) throws ZuulException {
        SumPagedRep<AppRevokeTokenCardRep> appRevokeTokenCardRepSumPagedRep = revokeTokenRepo.readByQuery("targetId:" + id, "num:0,size:1,by:" + ENTITY_ISSUE_AT + ",order:desc", null);
        if (appRevokeTokenCardRepSumPagedRep.getData().size() != 0) {
            if (appRevokeTokenCardRepSumPagedRep.getData().get(0).getIssuedAt() >= iat) {
                // reject request, for internal forwarding, set attribute */
                ctx.getRequest().setAttribute(EDGE_PROXY_TOKEN_REVOKED, Boolean.TRUE);
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                throw new ZuulException("not authorized", 401, "not authorized");
            }
        }
    }
}
