package com.hw.aggregate.endpoint.model;

import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.stereotype.Component;

@Component
public class BizEndpointQueryRegistry extends RestfulQueryRegistry<BizEndpoint> {

    @Override
    public Class<BizEndpoint> getEntityClass() {
        return BizEndpoint.class;
    }
}
