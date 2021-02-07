package com.mt.edgeproxy.port.adapter.http;

import com.nimbusds.jose.jwk.JWKSet;

public interface JwtPublicKeyAdapter {
    JWKSet fetchKeys();
}
