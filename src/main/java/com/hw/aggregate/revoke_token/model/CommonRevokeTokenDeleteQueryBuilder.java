package com.hw.aggregate.revoke_token.model;

import com.hw.shared.sql.builder.SoftDeleteQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class CommonRevokeTokenDeleteQueryBuilder extends SoftDeleteQueryBuilder<RevokeToken> {
}
