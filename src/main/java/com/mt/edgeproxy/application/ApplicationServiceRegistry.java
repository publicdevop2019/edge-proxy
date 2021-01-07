package com.mt.edgeproxy.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceRegistry {
    private static RevokeTokenApplicationService revokeTokenApplicationService;

    public static RevokeTokenApplicationService revokeTokenApplicationService() {
        return revokeTokenApplicationService;
    }

    @Autowired
    public void setRevokeTokenApplicationService(RevokeTokenApplicationService revokeTokenApplicationService) {
        ApplicationServiceRegistry.revokeTokenApplicationService = revokeTokenApplicationService;
    }

}
