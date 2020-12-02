package com.hw.aggregate.revoke_token.model;

import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RevokeTokenQueryRegistry extends RestfulQueryRegistry<RevokeToken> {

    @Override
    public Class<RevokeToken> getEntityClass() {
        return RevokeToken.class;
    }
}
