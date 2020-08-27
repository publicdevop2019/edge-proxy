package com.hw.aggregate.endpoint.representation;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import lombok.Data;

@Data
public class RootBizEndpointRep {
    private String expression;
    private String resourceId;
    private String description;
    private String path;
    private String method;
    private Long id;
    private String createdBy;
    private Long createdAt;
    private String modifiedBy;
    private Long modifiedAt;
    public RootBizEndpointRep(BizEndpoint bizEndpoint) {
        this.expression = bizEndpoint.getExpression();
        this.resourceId = bizEndpoint.getResourceId();
        this.path = bizEndpoint.getPath();
        this.method = bizEndpoint.getMethod();
        this.id = bizEndpoint.getId();
        this.description=bizEndpoint.getDescription();
        this.createdBy = bizEndpoint.getCreatedBy();
        this.createdAt = bizEndpoint.getCreatedAt() != null ? bizEndpoint.getCreatedAt().getTime() : null;
        this.modifiedBy = bizEndpoint.getModifiedBy();
        this.modifiedAt = bizEndpoint.getModifiedAt() != null ? bizEndpoint.getModifiedAt().getTime() : null;
    }
}
