package com.mt.edgeproxy.port.adapter.http;

import com.mt.edgeproxy.domain.Endpoint;

import java.util.Set;

public interface EndpointAdapter {
    Set<Endpoint> fetchAllEndpoints();
}
