package com.hw.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.hw.config.filter.RevokeTokenFilter.EDGE_PROXY_TOKEN_REVOKED;
import static com.hw.config.filter.EndpointFilter.EDGE_PROXY_UNAUTHORIZED_ACCESS;

/**
 * secure internal forward helper
 */
@Component
public class InternalForwardHelper {
    public void forwardCheck(HttpServletRequest request) {
        Boolean internalForwardBlock = (Boolean) request.getAttribute(EDGE_PROXY_TOKEN_REVOKED);
        Boolean internalForwardBlock2 = (Boolean) request.getAttribute(EDGE_PROXY_UNAUTHORIZED_ACCESS);
        if (internalForwardBlock != null && internalForwardBlock)
            throw new UnauthorizedClientException("internal endpoint access denied, revoked token found");
        if (internalForwardBlock2 != null && internalForwardBlock2)
            throw new AccessDeniedException("internal endpoint access denied, unauthorized access");
    }
}