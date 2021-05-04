package com.mt.proxy.infrastructure;

import com.mt.proxy.domain.CsrfService;
import com.mt.proxy.domain.EndpointService;
import com.mt.proxy.domain.MethodPathKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class CustomEndpointCsrfMatcher implements ServerWebExchangeMatcher {
    @Autowired
    private CsrfService csrfService;
    @Autowired
    private EndpointService endpointService;

    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest())
                .filter(e -> {
                    Set<MethodPathKey> patchKeyStream = csrfService.getBypassList();
                    AntPathMatcher matcher = endpointService.getPathMater();
                    Optional<MethodPathKey> first = patchKeyStream.stream().filter(pattern -> matcher.match(pattern.getPath(), e.getPath().value())).filter(key -> key.getMethod().equalsIgnoreCase(e.getMethodValue())).findFirst();
                    return first.isPresent();
                })
                .flatMap(e -> MatchResult.notMatch())
                .switchIfEmpty(MatchResult.match());
    }
}
