package com.mt.edgeproxy.infrastructure.springcloudgateway;

import com.mt.edgeproxy.domain.DomainRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class SCGEndpointFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = null;
        List<String> authorization = exchange.getRequest().getHeaders().get("authorization");
        if (authorization != null && !authorization.isEmpty()) {
            authHeader = authorization.get(0);
        }
        DomainRegistry.endpointService().checkAccess(
                exchange.getRequest().getPath().toString(),
                exchange.getRequest().getMethod().name(),
                authHeader, (ignored) -> {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    exchange.getResponse().setComplete();
                });
        return chain.filter(exchange);
    }
}
