package com.mt.proxy.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class CorsService implements CorsConfigurationSource {
    @Autowired
    private EndpointService endpointService;
    private final Map<MethodPathKey, CorsConfiguration> corsConfigurations = new HashMap<>();

    public void refreshCorsfConfig(Set<Endpoint> cached) {
        log.debug("refresh cors config");
        cached.forEach(endpoint -> {
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
        CorsConfiguration corsConfiguration = this.corsConfigurations.entrySet().stream()
                .filter(entry -> pathMater.match(entry.getKey().getPath(), exchange.getRequest().getPath().value())
                        &&
                        exchange.getRequest().getMethodValue().equalsIgnoreCase(entry.getKey().getMethod()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
        log.debug("found {}", corsConfiguration);
        return corsConfiguration;
    }
}
