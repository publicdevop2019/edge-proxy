package com.hw.aggregate.revoke_token.model;

import com.mt.common.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Component;

@Component
public class RevokeTokenQueryRegistry extends RestfulQueryRegistry<RevokeToken> {

    @Override
    public Class<RevokeToken> getEntityClass() {
        return RevokeToken.class;
    }
}
