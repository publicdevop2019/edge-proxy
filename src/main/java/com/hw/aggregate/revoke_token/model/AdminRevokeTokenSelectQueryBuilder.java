package com.hw.aggregate.revoke_token.model;

import com.mt.common.sql.builder.SelectQueryBuilder;
import com.mt.common.sql.clause.SelectFieldLongEqualClause;
import com.mt.common.sql.clause.SelectFieldStringEqualClause;
import org.springframework.stereotype.Component;

import static com.hw.aggregate.revoke_token.model.RevokeToken.ENTITY_ISSUE_AT;
import static com.hw.aggregate.revoke_token.model.RevokeToken.ENTITY_TARGET_ID;

@Component
public class AdminRevokeTokenSelectQueryBuilder extends SelectQueryBuilder<RevokeToken> {
    {
        mappedSortBy.put(ENTITY_ISSUE_AT, ENTITY_ISSUE_AT);
        supportedWhereField.put("targetId", new SelectFieldStringEqualClause<>(ENTITY_TARGET_ID));
        allowEmptyClause = true;
    }

}
