package com.mt.proxy.infrastructure.springcloudgateway;

import com.mt.proxy.domain.DomainRegistry;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;

@Slf4j
@Component
public class SCGEndpointFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = null;
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        List<String> temp;
        boolean webSocket = false;
        if ("websocket".equals(headers.getUpgrade())) {
            webSocket = true;
            temp = request.getQueryParams().get("jwt");
            if (temp != null && !temp.isEmpty()) {
                authHeader = "Bearer " + new String(Base64.decode(temp.get(0)));
            }
        } else {
            temp = headers.get("authorization");
            if (temp != null && !temp.isEmpty()) {
                authHeader = temp.get(0);
            }
        }
        boolean allow;
        try {
            //noinspection ConstantConditions
            allow = DomainRegistry.endpointService().checkAccess(
                    request.getPath().toString(),
                    request.getMethod().name(),
                    authHeader, webSocket);
        } catch (ParseException e) {
            log.error("error during parse", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return response.setComplete();
        }
        if (!allow) {
            log.debug("access is not allowed");
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        log.debug("access is allowed");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
