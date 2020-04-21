package com.hw.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.entity.RevokeTokenInfo;
import com.hw.repo.RevokeClientRepo;
import com.hw.repo.RevokeResourceOwnerRepo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static com.hw.clazz.Constant.EDGE_PROXY_TOKEN_REVOKED;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class RevokeFilter extends ZuulFilter {

    @Autowired
    private RevokeClientRepo revokeClientRepo;

    @Autowired
    private RevokeResourceOwnerRepo revokeResourceOwnerRepo;

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
    public Object run() {
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
                RevokeTokenInfo byName = null;
                if (userId != null) {
                    byName = revokeResourceOwnerRepo.findByGlobalId(userId);
                }
                if (clientId != null) {
                    byName = revokeClientRepo.findByGlobalId(clientId);
                }
                if (byName != null && byName.getIssuedAt() >= iat) {
                    // reject request, for internal forwarding, set attribute */
                    request.setAttribute(EDGE_PROXY_TOKEN_REVOKED, Boolean.TRUE);
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                }

            } catch (IOException e) {
                // this block is left blank on purpose*/
            }

        }
        return null;
    }
}
