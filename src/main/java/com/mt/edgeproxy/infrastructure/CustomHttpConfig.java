package com.mt.edgeproxy.infrastructure;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class CustomHttpConfig {

//    @Bean
//    public CorsWebFilter corsConfiguration() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:4300");
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedHeader("Authorization");
//        configuration.addAllowedHeader("lastupdateat");
//        configuration.addAllowedHeader("uuid");
//        configuration.addAllowedHeader("changeId");
//        configuration.addAllowedHeader("X-XSRF-TOKEN");
//        configuration.addAllowedHeader("Content-Type");
//        configuration.addAllowedHeader("Accept");
//        configuration.addAllowedHeader("Access-Control-Request-Method");
//        configuration.addAllowedHeader("x-requested-with");
//        configuration.addExposedHeader("location");
//        configuration.addExposedHeader("lastupdateat");
//        configuration.addExposedHeader("uuid");
//        configuration.addAllowedMethod("POST");
//        configuration.addAllowedMethod("PATCH");
//        configuration.addAllowedMethod("GET");
//        configuration.addAllowedMethod("DELETE");
//        configuration.addAllowedMethod("PUT");
//        configuration.addAllowedMethod("OPTIONS");
//        configuration.setMaxAge(86400L); // permission may be cached for 1 day (86400 seconds)
//        source.registerCorsConfiguration("/**/**/**", configuration);
//        return new CorsWebFilter(source);
//    }

//    @Bean
//    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowETagHeaderFilter(CustomCachedETagHeaderFilter filter) {
//        FilterRegistrationBean<ShallowEtagHeaderFilter> bean
//                = new FilterRegistrationBean<>(filter);
//        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
//        return bean;
//    }

}
