package com.mt.edgeproxy.port.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryBuilderRegistry {
    private static RevokeTokenQueryBuilder revokeTokenQueryBuilder;

    public static RevokeTokenQueryBuilder revokeTokenQueryBuilder() {
        return revokeTokenQueryBuilder;
    }

    @Autowired
    public void setRevokeTokenQueryBuilder(RevokeTokenQueryBuilder revokeTokenQueryBuilder) {
        QueryBuilderRegistry.revokeTokenQueryBuilder = revokeTokenQueryBuilder;
    }
}
