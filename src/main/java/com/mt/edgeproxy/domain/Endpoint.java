package com.mt.edgeproxy.domain;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Endpoint {
    private String id;
    private String expression;

    private String resourceId;

    private String path;

    private String method;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endpoint)) return false;
        Endpoint endpoint = (Endpoint) o;
        return Objects.equal(expression, endpoint.expression) && Objects.equal(resourceId, endpoint.resourceId) && Objects.equal(path, endpoint.path) && Objects.equal(method, endpoint.method);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expression, resourceId, path, method);
    }
}
