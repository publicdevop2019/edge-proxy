package com.hw.aggregate.endpoint.representation;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import lombok.Data;

@Data
public class RootBizEndpointCardRep {
    private String expression;
    private String resourceId;
    private String path;
    private String method;
    private Long id;

    public RootBizEndpointCardRep(BizEndpoint bizEndpoint) {
        this.expression = bizEndpoint.getExpression();
        this.resourceId = bizEndpoint.getResourceId();
        this.path = bizEndpoint.getPath();
        this.method = bizEndpoint.getMethod();
        this.id = bizEndpoint.getId();
    }
}
