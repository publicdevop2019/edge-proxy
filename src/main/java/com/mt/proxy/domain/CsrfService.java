package com.mt.proxy.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CsrfService {
    private final Set<MethodPathKey> csrfList = new HashSet<>();

    public void refreshCsrfConfig(Set<Endpoint> endpoints) {
        log.debug("refresh csrf config");
        csrfList.clear();
        endpoints.stream().filter(e -> !e.isCsrfEnabled()).forEach(e -> {
            csrfList.add(new MethodPathKey(e.getMethod(),e.getPath()));
        });
        log.debug("refresh csrf config completed");
    }

    public Set<MethodPathKey> getCsrfList() {
        return csrfList;
    }
}
