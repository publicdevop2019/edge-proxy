package com.hw.aggregate.endpoint.representation;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class RootBizEndpointCardRep {
    private String expression;
    private String description;
    private String resourceId;
    private String path;
    private String method;
    private Long id;
    private Integer version;

    public RootBizEndpointCardRep(BizEndpoint bizEndpoint) {
        BeanUtils.copyProperties(bizEndpoint, this);
    }
}
