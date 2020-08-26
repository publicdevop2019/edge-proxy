package com.hw.aggregate.endpoint;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import lombok.Data;

@Data
public class AppBizEndpointCardRep {
    private Long id;
    private String expression;

    private String resourceId;

    private String path;

    private String method;

    public AppBizEndpointCardRep(BizEndpoint bizEndpoint) {
        this.id = bizEndpoint.getId();
        this.expression = bizEndpoint.getExpression();
        this.resourceId = bizEndpoint.getResourceId();
        this.path = bizEndpoint.getPath();
        this.method = bizEndpoint.getMethod();

    }

}
