package com.hw.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.revoke_token.RevokeTokenRepo;
import com.hw.aggregate.revoke_token.model.RevokeToken;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class RevokeFilter extends ZuulFilter {

    @Autowired
    private RevokeTokenRepo revokeTokenRepo;

    @Autowired
    private ObjectMapper mapper;

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
            if (authHeader != null && authHeader.contains("Bearer")) {
                jwt = JwtHelper.decode(authHeader.replace("Bearer ", ""));
            } else {
                jwt = JwtHelper.decode(httpServletRequestWrapper.getRequest().getParameter("refresh_token"));
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

            } catch (IOException e) {
                // this block is left blank on purpose*/
            }

        }
        return null;
    }

    private void checkToken(Long id, RequestContext ctx, Integer iat) throws ZuulException {
        Optional<RevokeToken> byId = revokeTokenRepo.findById(id);
        if (byId.isPresent() && byId.get().getIssuedAt() >= iat) {
            // reject request, for internal forwarding, set attribute */
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            throw new ZuulException("not authorized", 401, "not authorized");
        }
    }
}
