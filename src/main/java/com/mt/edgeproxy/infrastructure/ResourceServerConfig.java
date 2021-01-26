package com.mt.edgeproxy.infrastructure;

import lombok.extern.slf4j.Slf4j;
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

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        CookieServerCsrfTokenRepository cookieCsrfTokenRepository = new CookieServerCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieHttpOnly(false);
        cookieCsrfTokenRepository.setCookiePath("/");
        httpSecurity
                .authorizeExchange()
                .pathMatchers("/auth-svc/oauth/token").permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().csrfTokenRepository(cookieCsrfTokenRepository)
                .requireCsrfProtectionMatcher(new AllExceptAntMatcher("/auth-svc/oauth/token"))
                .and()
                .cors().configurationSource(corsConfiguration())
                .and()
                .oauth2ResourceServer().jwt()
        ;
        return httpSecurity.build();
    }

    @Bean
    WebFilter addCsrfToken() {
        return (exchange, next) -> exchange
                .<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
                .doOnSuccess(token -> {
                    log.debug("csrf token {}", token.getToken());
                }) // do nothing, just subscribe :/
                .then(next.filter(exchange));
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4300");
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("lastupdateat");
        configuration.addAllowedHeader("uuid");
        configuration.addAllowedHeader("changeId");
        configuration.addAllowedHeader("X-XSRF-TOKEN");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedHeader("Access-Control-Request-Method");
        configuration.addAllowedHeader("x-requested-with");
        configuration.addExposedHeader("location");
        configuration.addExposedHeader("lastupdateat");
        configuration.addExposedHeader("uuid");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("OPTIONS");
        configuration.setMaxAge(86400L); // permission may be cached for 1 day (86400 seconds)
        source.registerCorsConfiguration("/**/**/**", configuration);
        return source;
    }

    private static class AllExceptAntMatcher implements ServerWebExchangeMatcher {
        private static final Set<HttpMethod> ALLOWED_METHODS = new HashSet<>(
                Arrays.asList(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.TRACE, HttpMethod.OPTIONS));

        private final String ignorePattern;

        public AllExceptAntMatcher(String path) {
            this.ignorePattern = path;
        }

        @Override
        public Mono<MatchResult> matches(ServerWebExchange exchange) {
            AntPathMatcher antPathMatcher = new AntPathMatcher(this.ignorePattern);
            return Mono.just(exchange.getRequest())
                    .filter(e -> antPathMatcher.match(this.ignorePattern, e.getPath().value()) || ALLOWED_METHODS.contains(e.getMethod()))
                    .flatMap(e -> MatchResult.notMatch())
                    .switchIfEmpty(MatchResult.match());
        }
    }
}
