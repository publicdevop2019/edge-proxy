package com.mt.edgeproxy.domain;

import com.nimbusds.jose.jwk.JWKSet;

public interface RetrieveJwtPublicKeyService {
    JWKSet loadKeys();
}
