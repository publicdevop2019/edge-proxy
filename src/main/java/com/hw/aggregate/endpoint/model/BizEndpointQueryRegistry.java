package com.hw.aggregate.endpoint.model;

import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BizEndpointQueryRegistry extends RestfulQueryRegistry<BizEndpoint> {
    @Autowired
    private AppBizEndpointSelectQueryBuilder appBizEndpointSelectQueryBuilder;
    @Autowired
    private RootBizEndpointSelectQueryBuilder rootBizEndpointSelectQueryBuilder;
    @Autowired
    private RootBizEndpointDeleteQueryBuilder rootBizEndpointDeleteQueryBuilder;
    @Override
    @PostConstruct
    protected void configQueryBuilder() {
        selectQueryBuilder.put(RoleEnum.APP,appBizEndpointSelectQueryBuilder);
        selectQueryBuilder.put(RoleEnum.ROOT,rootBizEndpointSelectQueryBuilder);
        deleteQueryBuilder.put(RoleEnum.ROOT,rootBizEndpointDeleteQueryBuilder);

    }
}
