package com.hw.aggregate.endpoint.model;

import com.hw.shared.sql.builder.SoftDeleteQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class RootBizEndpointDeleteQueryBuilder extends SoftDeleteQueryBuilder<BizEndpoint> {
}
