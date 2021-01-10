package com.mt.edgeproxy.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainRegistry {
    private static RevokeTokenRepository revokeTokenRepository;
    private static RevokeTokenService revokeTokenService;

    public static RevokeTokenRepository revokeTokenRepository() {
        return revokeTokenRepository;
    }

    public static RevokeTokenService revokeTokenService() {
        return revokeTokenService;
    }


    @Autowired
    public void setRevokeTokenRepository(RevokeTokenRepository revokeTokenRepository) {
        DomainRegistry.revokeTokenRepository = revokeTokenRepository;
    }

    @Autowired
    public void setRevokeTokenService(RevokeTokenService revokeTokenService) {
        DomainRegistry.revokeTokenService = revokeTokenService;
    }

    private static EndpointService roadEndpointService;

    public static EndpointService roadEndpointService() {
        return roadEndpointService;
    }


    @Autowired
    public void setLoadEndpointService(EndpointService roadEndpointService) {
        DomainRegistry.roadEndpointService = roadEndpointService;
    }

}
