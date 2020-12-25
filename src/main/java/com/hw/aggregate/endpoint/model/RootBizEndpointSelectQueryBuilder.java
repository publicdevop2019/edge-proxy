package com.hw.aggregate.endpoint.model;

import com.mt.common.sql.builder.SelectQueryBuilder;
import com.mt.common.sql.clause.SelectFieldStringEqualClause;
import org.springframework.stereotype.Component;

import static com.hw.aggregate.endpoint.model.BizEndpoint.*;

@Component
public class RootBizEndpointSelectQueryBuilder extends SelectQueryBuilder<BizEndpoint> {
    {
        DEFAULT_PAGE_SIZE = 20;
        MAX_PAGE_SIZE = 50;
        mappedSortBy.put("resourceId", ENTITY_RESOURCE_ID);
        mappedSortBy.put("path", ENTITY_PATH);
        mappedSortBy.put("method", ENTITY_METHOD);
        supportedWhereField.put(ENTITY_RESOURCE_ID, new SelectFieldStringEqualClause<>(ENTITY_RESOURCE_ID));
        supportedWhereField.put(ENTITY_METHOD, new SelectFieldStringEqualClause<>(ENTITY_METHOD));
        allowEmptyClause = true;
    }

}
