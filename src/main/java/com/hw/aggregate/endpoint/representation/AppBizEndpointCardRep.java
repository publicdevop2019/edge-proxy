package com.hw.aggregate.endpoint.representation;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AppBizEndpointCardRep {
    private Long id;
    private String expression;

    private String resourceId;

    private String path;

    private String method;

    public AppBizEndpointCardRep(BizEndpoint bizEndpoint) {
        BeanUtils.copyProperties(bizEndpoint, this);
    }

}
