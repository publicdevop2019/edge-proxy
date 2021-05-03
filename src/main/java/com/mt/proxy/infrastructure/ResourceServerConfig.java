package com.mt.proxy.infrastructure;

import com.mt.proxy.domain.CorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    @Autowired
    private CustomEndpointCsrfMatcher customEndpointCsrfMatcher;
    @Autowired
    private CorsService corsService;
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        CookieServerCsrfTokenRepository cookieCsrfTokenRepository = new CookieServerCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieHttpOnly(false);
        cookieCsrfTokenRepository.setCookiePath("/");
        httpSecurity
                .authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .csrf().csrfTokenRepository(cookieCsrfTokenRepository)
                .requireCsrfProtectionMatcher(customEndpointCsrfMatcher)
                .and()

                .cors().configurationSource(corsService)
                .and()
                .oauth2ResourceServer().jwt()
        ;
        return httpSecurity.build();
    }

    /**
     * require for csrf to be attached
     */
    @Bean
    WebFilter addCsrfToken() {
        return (exchange, next) -> exchange
                .<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
                .doOnSuccess(token -> {
                    log.debug("csrf token {}", token.getToken());
                })
                .then(next.filter(exchange));
    }
}
