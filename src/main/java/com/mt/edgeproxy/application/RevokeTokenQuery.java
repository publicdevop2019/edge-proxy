package com.mt.edgeproxy.application;

import lombok.Getter;

public class RevokeTokenQuery {
    @Getter
    private String value;

    public RevokeTokenQuery(String queryParam) {
        this.value = queryParam;
    }

}
