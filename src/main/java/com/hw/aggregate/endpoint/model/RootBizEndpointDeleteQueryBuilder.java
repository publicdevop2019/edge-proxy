package com.hw.aggregate.endpoint.model;

import com.hw.shared.sql.builder.DeleteByIdQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class RootBizEndpointDeleteQueryBuilder extends DeleteByIdQueryBuilder<BizEndpoint> {
    @Autowired
    private void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }
}
