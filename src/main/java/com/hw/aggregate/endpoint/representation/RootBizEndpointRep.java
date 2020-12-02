package com.hw.aggregate.endpoint.representation;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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
    private Integer version;

    public RootBizEndpointRep(BizEndpoint bizEndpoint) {
        BeanUtils.copyProperties(bizEndpoint, this);
        this.createdAt = bizEndpoint.getCreatedAt() != null ? bizEndpoint.getCreatedAt().getTime() : null;
        this.modifiedAt = bizEndpoint.getModifiedAt() != null ? bizEndpoint.getModifiedAt().getTime() : null;
    }
}
