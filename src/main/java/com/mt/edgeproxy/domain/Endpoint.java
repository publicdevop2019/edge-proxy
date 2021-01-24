package com.mt.edgeproxy.domain;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Endpoint {
    private List<String> roles;
    private List<String> scope;
    private boolean secured;
    private boolean userOnly;
    private boolean clientOnly;

    private String resourceId;

    private String path;

    private String method;

    public boolean allowAccess(String jwtRaw) {
        boolean user = DomainRegistry.jwtService().isUser(jwtRaw);
        if (isClientOnly() && user) {
            return false;
        }
        if (isUserOnly() && !user) {
            return false;
        }
        List<String> roles = DomainRegistry.jwtService().getRoles(jwtRaw);
        if (!roles.equals(getRoles()))
            return false;
        List<String> scopes = DomainRegistry.jwtService().getScopes(jwtRaw);
        return scopes.equals(getScope());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endpoint)) return false;
        Endpoint endpoint = (Endpoint) o;
        return Objects.equal(resourceId, endpoint.resourceId) && Objects.equal(path, endpoint.path) && Objects.equal(method, endpoint.method);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(resourceId, path, method);
    }
}
