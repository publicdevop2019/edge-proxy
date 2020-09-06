package com.hw.aggregate.revoke_token.model;

import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RevokeTokenQueryRegistry extends RestfulQueryRegistry<RevokeToken> {
    @Autowired
    private CommonRevokeTokenSelectQueryBuilder commonRevokeTokenSelectQueryBuilder;
    @Autowired
    private CommonRevokeTokenDeleteQueryBuilder commonRevokeTokenDeleteQueryBuilder;

    @Override
    @PostConstruct
    protected void configQueryBuilder() {

        selectQueryBuilder.put(RoleEnum.ROOT, commonRevokeTokenSelectQueryBuilder);
        selectQueryBuilder.put(RoleEnum.ADMIN, commonRevokeTokenSelectQueryBuilder);
        selectQueryBuilder.put(RoleEnum.APP, commonRevokeTokenSelectQueryBuilder);
        deleteQueryBuilder.put(RoleEnum.ROOT, commonRevokeTokenDeleteQueryBuilder);
        deleteQueryBuilder.put(RoleEnum.ADMIN, commonRevokeTokenDeleteQueryBuilder);
        deleteQueryBuilder.put(RoleEnum.APP, commonRevokeTokenDeleteQueryBuilder);
    }
}
