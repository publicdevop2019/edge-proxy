package com.hw.clazz;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;

import javax.servlet.http.HttpServletRequest;

import static com.hw.clazz.Constant.EDGE_PROXY_TOKEN_REVOKED;
import static com.hw.clazz.Constant.EDGE_PROXY_UNAUTHORIZED_ACCESS;

/**
 * secure internal forward helper
 */
public class InternalForwardHelper {
    public static void forwardCheck(HttpServletRequest request) {
        Boolean internal_forward_block = (Boolean) request.getAttribute(EDGE_PROXY_TOKEN_REVOKED);
        Boolean internal_forward_block2 = (Boolean) request.getAttribute(EDGE_PROXY_UNAUTHORIZED_ACCESS);
        if (internal_forward_block != null && internal_forward_block)
            throw new UnauthorizedClientException("internal endpoint access denied, revoked token found");
        if (internal_forward_block2 != null && internal_forward_block2)
            throw new AccessDeniedException("internal endpoint access denied, unauthorized access");
    }
}
