package com.mt.edgeproxy.domain;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Endpoint {
    private Set<String> clientRoles;
    private Set<String> userRoles;
    private Set<String> clientScopes;
    private boolean secured;
    private boolean userOnly;
    private boolean clientOnly;

    private String resourceId;

    private String path;

    private String method;

    public boolean allowAccess(String jwtRaw) throws ParseException {
        boolean user = DomainRegistry.jwtService().isUser(jwtRaw);
        if (isClientOnly() && user) {
            return false;
        }
        if (isUserOnly() && !user) {
            return false;
        }
        Set<String> roles = DomainRegistry.jwtService().getRoles(jwtRaw);
        if (user && !roles.containsAll(getUserRoles()))
            return false;
        if (!user && !roles.containsAll(getClientRoles()))
            return false;
        Set<String> scopes = DomainRegistry.jwtService().getScopes(jwtRaw);
        return scopes.containsAll(getClientScopes());
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
