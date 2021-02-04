package com.mt.edgeproxy.domain;

import java.util.Set;

public interface RetrieveEndpointService {
    Set<Endpoint> loadAllEndpoints();
}
