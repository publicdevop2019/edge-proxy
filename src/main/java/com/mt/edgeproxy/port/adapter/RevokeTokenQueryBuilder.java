package com.mt.edgeproxy.port.adapter;

import com.mt.common.sql.builder.SelectQueryBuilder;
import com.mt.common.sql.clause.SelectFieldStringEqualClause;
import com.mt.edgeproxy.domain.RevokeToken;
import org.springframework.stereotype.Component;

import static com.mt.edgeproxy.domain.RevokeToken.ENTITY_ISSUE_AT;
import static com.mt.edgeproxy.domain.RevokeToken.ENTITY_TARGET_ID;

@Component
public class RevokeTokenQueryBuilder extends SelectQueryBuilder<RevokeToken> {
    {
        mappedSortBy.put(ENTITY_ISSUE_AT, ENTITY_ISSUE_AT);
        supportedWhereField.put("targetId", new SelectFieldStringEqualClause<>(ENTITY_TARGET_ID));
    }

}
