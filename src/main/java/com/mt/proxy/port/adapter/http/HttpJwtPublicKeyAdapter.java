package com.mt.proxy.port.adapter.http;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;

@Component
@Slf4j
public class HttpJwtPublicKeyAdapter implements JwtPublicKeyAdapter {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwtKeyUrl;

    @Override
    public JWKSet fetchKeys() {
        ResponseEntity<String> exchange = restTemplate.exchange(jwtKeyUrl, HttpMethod.GET, null, String.class);
        try {
            return JWKSet.parse(exchange.getBody());
        } catch (ParseException e) {
            log.error("error during parse jwk", e);
            throw new UnableRetrieveJwkException();
        }
    }

    private static class UnableRetrieveJwkException extends RuntimeException {
    }
}
