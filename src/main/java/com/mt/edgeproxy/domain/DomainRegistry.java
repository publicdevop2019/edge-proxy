package com.mt.edgeproxy.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainRegistry {
    private static RevokeTokenRepository revokeTokenRepository;

    public static RevokeTokenRepository revokeTokenRepository() {
        return revokeTokenRepository;
    }


    @Autowired
    public void setRevokeTokenRepository(RevokeTokenRepository revokeTokenRepository) {
        DomainRegistry.revokeTokenRepository = revokeTokenRepository;
    }

}
