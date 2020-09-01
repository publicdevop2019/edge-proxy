package com.hw.aggregate.endpoint.model;

import com.hw.shared.sql.builder.SelectQueryBuilder;
import com.hw.shared.sql.clause.SelectFieldCollectionContainsClause;
import com.hw.shared.sql.clause.SelectFieldEnumStringEqualClause;
import com.hw.shared.sql.clause.SelectFieldStringEqualClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

import static com.hw.aggregate.endpoint.model.BizEndpoint.*;

@Component
public class RootBizEndpointSelectQueryBuilder extends SelectQueryBuilder<BizEndpoint> {
    RootBizEndpointSelectQueryBuilder() {
        DEFAULT_PAGE_SIZE = 20;
        MAX_PAGE_SIZE = 50;
        mappedSortBy.put("resourceId", ENTITY_RESOURCE_ID);
        mappedSortBy.put("path", ENTITY_PATH);
        mappedSortBy.put("method", ENTITY_METHOD);
        supportedWhereField.put(ENTITY_RESOURCE_ID, new SelectFieldCollectionContainsClause<>(ENTITY_RESOURCE_ID));
        supportedWhereField.put(ENTITY_METHOD, new SelectFieldCollectionContainsClause<>(ENTITY_METHOD));
        allowEmptyClause = true;
    }

    @Autowired
    private void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }
}
