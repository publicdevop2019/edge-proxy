package com.hw.aggregate.revoke_token.model;

import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Component;

@Component
public class RevokeTokenQueryRegistry extends RestfulQueryRegistry<RevokeToken> {
    @Override
    protected void configQueryBuilder() {

    }
}
