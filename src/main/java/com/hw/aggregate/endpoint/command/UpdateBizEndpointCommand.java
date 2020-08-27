package com.hw.aggregate.endpoint.command;

import lombok.Data;

@Data
public class UpdateBizEndpointCommand {
    private String expression;
    private String description;

    private String resourceId;

    private String path;

    private String method;
}
