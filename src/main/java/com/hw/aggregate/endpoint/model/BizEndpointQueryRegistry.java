package com.hw.aggregate.endpoint.model;

import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BizEndpointQueryRegistry extends RestfulQueryRegistry<BizEndpoint> {

    @Override
    public Class<BizEndpoint> getEntityClass() {
        return BizEndpoint.class;
    }
    @PostConstruct
    private void setUp() {
        cacheable.put(RoleEnum.USER, true);
        cacheable.put(RoleEnum.ADMIN, true);
        cacheable.put(RoleEnum.APP, true);
        cacheable.put(RoleEnum.PUBLIC, true);
        cacheable.put(RoleEnum.ROOT, true);
    }
}
