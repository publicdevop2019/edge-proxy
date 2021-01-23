package com.mt.edgeproxy.infrastructure.zuul;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * zuul proxy resource server can not set resourceId,
 * overwrite default resource id also required
 * if set then forwarded request will be force to check against this resource
 */
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
                .oauth2ResourceServer().jwt()
        ;
        httpSecurity.cors().configurationSource(corsConfiguration());
        return httpSecurity.build();
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
//    @Bean
//    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
//        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
//                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
//                        clientRegistrations,
//                        new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
//        oauth.setDefaultClientRegistrationId("manytree");
//        return WebClient.builder()
//                .filter(oauth)
//                .build();
//    }
    //    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(null);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
//        cookieCsrfTokenRepository.setCookieHttpOnly(false);
//        cookieCsrfTokenRepository.setCookiePath("/");
//        http.authorizeRequests()
//                .antMatchers("/auth-svc/oauth/token").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .csrf().ignoringAntMatchers("/auth-svc/oauth/token")
//                .csrfTokenRepository(cookieCsrfTokenRepository)
//        ;
//    }
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
