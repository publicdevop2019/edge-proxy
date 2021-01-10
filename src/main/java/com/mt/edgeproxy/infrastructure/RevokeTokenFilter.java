package com.mt.edgeproxy.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.edgeproxy.domain.DomainRegistry;
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
import java.io.IOException;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
@Component
public class RevokeTokenFilter extends ZuulFilter {

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

                Long iat = (Long) claims.get("iat");
                String userId = (String) claims.get("uid");
                String clientId = (String) claims.get("client_id");
                if (userId != null) {
                    checkToken(userId, ctx, iat);
                }
                if (clientId != null) {
                    checkToken(clientId, ctx, iat);
                }
                log.info("elapse in token filter::" + (System.currentTimeMillis() - startTime));
            } catch (IOException e) {
                // this block is left blank on purpose*/
            }

        }
        return null;
    }

    private void checkToken(String id, RequestContext ctx, Long iat) throws ZuulException {
        if (DomainRegistry.revokeTokenService().isBlocked(id, iat)) {
            ctx.getRequest().setAttribute(EDGE_PROXY_TOKEN_REVOKED, Boolean.TRUE);
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            throw new ZuulException("not authorized", 401, "not authorized");
        }
    }
}
