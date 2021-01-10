package com.mt.edgeproxy.domain;

import java.util.Optional;

public interface RevokeTokenRepository {
    Optional<RevokeToken> revokeToken(String id);
}
