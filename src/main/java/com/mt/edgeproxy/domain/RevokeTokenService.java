package com.mt.edgeproxy.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class RevokeTokenService {

    public boolean checkAccess(String authHeader, String requestURI, Map<String, String> requestBody) {
        if ((authHeader != null && authHeader.contains("Bearer")) ||
                (requestURI.contains("/oauth/token") && requestBody != null && requestBody.get("refresh_token") != null)
        ) {
            long startTime = System.currentTimeMillis();
            String jwtRaw;
            if (authHeader != null && authHeader.contains("Bearer")) {
                jwtRaw = authHeader.replace("Bearer ", "");
            } else {
                jwtRaw = requestBody.get("refresh_token");
            }
            Long issueAt = DomainRegistry.jwtService().getIssueAt(jwtRaw);
            String userId = DomainRegistry.jwtService().getUserId(jwtRaw);
            String clientId = DomainRegistry.jwtService().getClientId(jwtRaw);
            boolean allowUser = true;
            boolean allowClient = true;
            if (userId != null) {
                allowUser = notBlocked(userId, issueAt);
            }
            if (clientId != null) {
                allowClient = notBlocked(clientId, issueAt);
            }
            log.debug("elapse in token filter::" + (System.currentTimeMillis() - startTime));
            return allowUser && allowClient;
        }
        return true;
    }

    private boolean notBlocked(String id, Long iat) {
        Optional<RevokeToken> optionalRevokeToken = DomainRegistry.revokeTokenRepository().revokeToken(id);
        return optionalRevokeToken.isEmpty() || optionalRevokeToken.get().getIssuedAt() < iat;
    }
}
