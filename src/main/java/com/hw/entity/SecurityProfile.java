package com.hw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;

@Data
@AllArgsConstructor
public class SecurityProfile {
    /**
     * spring security style expression e.g. "hasRole('ROLE_USER') and #oauth2.hasScope('trust') and #oauth2.isUser()"
     */
    private String expression;

    private String resourceID;

    private String endpoint;

    private HttpMethod method;


}
