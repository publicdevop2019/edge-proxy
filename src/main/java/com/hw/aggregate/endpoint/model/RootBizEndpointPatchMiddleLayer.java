package com.hw.aggregate.endpoint.model;

import com.hw.shared.rest.TypedClass;
import lombok.Data;

@Data
public class RootBizEndpointPatchMiddleLayer extends TypedClass<RootBizEndpointPatchMiddleLayer> {
    private String expression;
    private String description;
    private String resourceId;
    private String path;
    private String method;

    public RootBizEndpointPatchMiddleLayer(BizEndpoint bizEndpoint) {
        super(RootBizEndpointPatchMiddleLayer.class);
        this.expression = bizEndpoint.getExpression();
        this.description = bizEndpoint.getDescription();
        this.resourceId = bizEndpoint.getResourceId();
        this.path = bizEndpoint.getPath();
        this.method = bizEndpoint.getMethod();
    }

    public RootBizEndpointPatchMiddleLayer() {
        super(RootBizEndpointPatchMiddleLayer.class);
    }

}
