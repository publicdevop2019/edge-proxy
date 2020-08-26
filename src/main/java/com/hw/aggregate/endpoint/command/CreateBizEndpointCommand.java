package com.hw.aggregate.endpoint.command;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
@Data
public class CreateBizEndpointCommand {
    private String expression;

    private String resourceId;

    private String path;

    private String method;
}
