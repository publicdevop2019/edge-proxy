package com.mt.edgeproxy.port.adapter.http;

import com.mt.edgeproxy.domain.Endpoint;
import com.mt.edgeproxy.domain.EndpointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class TranslatingEndpointService implements EndpointService {
    @Autowired
    private EndpointAdapter endpointAdapter;

    @Override
    public Set<Endpoint> loadAllEndpoints() {
        log.debug("load all endpoints started");
        Set<Endpoint> endpoints = endpointAdapter.fetchAllEndpoints();
        log.debug("load all endpoints complete, total {} loaded", endpoints.size());
        return endpoints;
    }
}
