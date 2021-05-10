package com.mt.proxy.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CorsService implements CorsConfigurationSource {
    @Autowired
    private EndpointService endpointService;
    private final Map<MethodPathKey, CorsConfiguration> corsConfigurations = new HashMap<>();

    public void refreshCorsfConfig(Set<Endpoint> cached) {
        log.debug("refresh cors config");
        corsConfigurations.clear();
        cached.stream().filter(this::hasCorsInfo).forEach(endpoint -> {
            MethodPathKey options = new MethodPathKey("OPTIONS", endpoint.getPath());
            if (corsConfigurations.get(options) != null) {
                CorsConfiguration corsConfiguration = corsConfigurations.get(options);
                appendCorsConfiguration(corsConfiguration, endpoint);
                corsConfigurations.put(new MethodPathKey(endpoint.getMethod(), endpoint.getPath()), corsConfiguration);
            } else {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                initializeCorsConfig(corsConfiguration, endpoint);
                corsConfigurations.put(options, corsConfiguration);
                corsConfigurations.put(new MethodPathKey(endpoint.getMethod(), endpoint.getPath()), corsConfiguration);
            }
        });
        log.debug("refresh cors config completed");
    }

    private boolean hasCorsInfo(Endpoint e) {
        return e.getCorsConfig() != null;
    }

    private void appendCorsConfiguration(CorsConfiguration corsConfiguration, Endpoint endpoint) {
        corsConfiguration.addAllowedMethod(endpoint.getMethod());
    }

    private void initializeCorsConfig(CorsConfiguration configuration, Endpoint endpoint) {
        Endpoint.CorsConfig corsConfig = endpoint.getCorsConfig();
        if (corsConfig != null) {
            corsConfig.getOrigin().forEach(configuration::addAllowedOrigin);
            configuration.setAllowCredentials(corsConfig.isCredentials());
            configuration.setAllowedHeaders(List.copyOf(corsConfig.getAllowedHeaders()));
            configuration.setExposedHeaders(List.copyOf(corsConfig.getExposedHeaders()));
            configuration.addAllowedMethod(endpoint.getMethod());
            configuration.setMaxAge(corsConfig.getMaxAge());
        }
    }

    @Override
    public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
        AntPathMatcher pathMater = endpointService.getPathMater();
        Map<MethodPathKey, CorsConfiguration> methodPathKeyCorsConfigHashMap = new HashMap<>();
        this.corsConfigurations.entrySet().stream()
                .filter(entry -> pathMater.match(entry.getKey().getPath(), exchange.getRequest().getPath().value())
                        &&
                        exchange.getRequest().getMethodValue().equalsIgnoreCase(entry.getKey().getMethod()))
                .forEach(e->{
                    methodPathKeyCorsConfigHashMap.put(e.getKey(),e.getValue());
                });
        CorsConfiguration corsConfiguration = getMostSpecificSecurityProfile(methodPathKeyCorsConfigHashMap).stream().findFirst().orElse(null);
        log.debug("found {} for path {} with method {}", corsConfiguration,exchange.getRequest().getPath().value(),exchange.getRequest().getMethodValue());
        return corsConfiguration;
    }

    private static Optional<CorsConfiguration> getMostSpecificSecurityProfile(Map<MethodPathKey, CorsConfiguration> map) {
        Set<MethodPathKey> methodPathKeys = map.keySet();
        if (methodPathKeys.size() == 1)
            return Optional.of(map.get(methodPathKeys.toArray()[0]));
        List<MethodPathKey> collect = methodPathKeys.stream().filter(e -> !e.getPath().contains("/**")).collect(Collectors.toList());
        if (collect.size() == 1)
            return Optional.of(map.get(collect.toArray()[0]));
        List<MethodPathKey> collect2 = methodPathKeys.stream().filter(e -> !e.getPath().endsWith("/**")).collect(Collectors.toList());
        if (collect2.size() == 1)
            return Optional.of(map.get(collect2.toArray()[0]));
        return Optional.empty();
    }
}
