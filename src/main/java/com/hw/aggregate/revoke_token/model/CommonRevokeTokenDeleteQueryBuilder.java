package com.hw.aggregate.revoke_token.model;

import com.hw.shared.sql.builder.SoftDeleteQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class CommonRevokeTokenDeleteQueryBuilder extends SoftDeleteQueryBuilder<RevokeToken> {
    @Autowired
    private void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }
}
