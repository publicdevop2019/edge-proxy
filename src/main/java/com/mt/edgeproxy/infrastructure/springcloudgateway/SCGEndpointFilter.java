package com.mt.edgeproxy.infrastructure.springcloudgateway;

import com.mt.edgeproxy.domain.DomainRegistry;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        ServerHttpRequest httpRequest = null;
        if ("websocket".equals(headers.getUpgrade())) {
            temp = request.getQueryParams().get("jwt");
            if (temp != null && !temp.isEmpty()) {
                URI oldUri = request.getURI();
                String[] split = oldUri.getQuery().split(",");
                String modified = Arrays.stream(split).filter(e -> !e.contains("jwt")).collect(Collectors.joining(","));
                authHeader = "Bearer " + new String(Base64.decode(temp.get(0)));
                URI uri;
                try {
                    uri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                            oldUri.getPath(), modified, oldUri.getFragment());
                } catch (URISyntaxException e) {
                    log.error("error during updating uri", e);
                    response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return response.setComplete();
                }
                httpRequest = request.mutate().uri(uri).build();
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
                    authHeader);
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
        if (httpRequest != null) {
            return chain.filter(exchange.mutate().request(httpRequest).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
