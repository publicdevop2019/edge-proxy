package com.mt.proxy.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class CsrfService {
    private final Map<String, String> pathMethodMap = new HashMap<>();

    public void refreshCsrfConfig(Set<Endpoint> endpoints) {
        log.debug("refresh csrf config");
        pathMethodMap.clear();
        endpoints.stream().filter(e -> !e.isCsrfEnabled()).forEach(e -> {
            pathMethodMap.put(e.getPath(), e.getMethod());
        });
        log.debug("refresh csrf config completed");
    }

    public Map<String, String> getPathMethodMap() {
        return pathMethodMap;
    }
}
