package com.hw.config;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ETagStore {
    private final Map<String, String> eTags = new ConcurrentHashMap<>();

    public void setETags(String url, String etageValue) {
        eTags.put(url, etageValue);
    }

    public String getETags(String url) {
        return eTags.get(url);
    }

    public void clearResourceETag(String resourceName) {
        Set<String> collect = eTags.keySet().stream().filter(e -> e.contains(resourceName)).collect(Collectors.toSet());
        collect.forEach(eTags::remove);
    }
}
