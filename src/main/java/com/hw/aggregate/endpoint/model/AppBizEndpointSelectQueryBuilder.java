package com.hw.aggregate.endpoint.model;

import com.hw.shared.sql.builder.SelectQueryBuilder;
import com.hw.shared.sql.clause.SelectFieldStringEqualClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

import static com.hw.aggregate.endpoint.model.BizEndpoint.*;

@Component
public class AppBizEndpointSelectQueryBuilder extends SelectQueryBuilder<BizEndpoint> {
    AppBizEndpointSelectQueryBuilder() {
        DEFAULT_PAGE_SIZE = 10;
        MAX_PAGE_SIZE = 20;
        supportedWhereField.put("resourceId", new SelectFieldStringEqualClause<>(ENTITY_RESOURCE_ID));
        supportedWhereField.put("method", new SelectFieldStringEqualClause<>(ENTITY_METHOD));
        supportedWhereField.put("path", new SelectFieldStringEqualClause<>(ENTITY_PATH));
    }

}
