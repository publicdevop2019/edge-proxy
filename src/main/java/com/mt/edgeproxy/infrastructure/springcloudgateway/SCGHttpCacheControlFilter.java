package com.mt.edgeproxy.infrastructure.springcloudgateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SCGHttpCacheControlFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            if (HttpMethod.GET.equals(exchange.getRequest().getMethod())) {
                log.debug("adding cache control to current get request");
                exchange.getResponse().getHeaders().remove("Cache-Control");
                exchange.getResponse().getHeaders().remove("Expires");
                exchange.getResponse().getHeaders().remove("Pragma");
                exchange.getResponse().getHeaders().setCacheControl(CacheControl.maxAge(5, TimeUnit.SECONDS));
            }
        }));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
