package com.mt.edgeproxy.domain;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RevokeTokenService {
    public boolean isBlocked(String id, Long iat) {
        Optional<RevokeToken> optionalRevokeToken = DomainRegistry.revokeTokenRepository().revokeToken(id);
        return optionalRevokeToken.isPresent() && optionalRevokeToken.get().getIssuedAt() >= iat;
    }
}
