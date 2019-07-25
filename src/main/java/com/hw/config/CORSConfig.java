package com.hw.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Profile("cors")
public class CORSConfig {

    @Bean
    FilterRegistrationBean corsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedHeader("Access-Control-Request-Method");
        configuration.addAllowedHeader("x-requested-with");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("OPTIONS");
        configuration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**/**/**", configuration);
//        source.registerCorsConfiguration("/oauth/token_key", configuration);
//        source.registerCorsConfiguration("/api/v1/client", configuration);
//        source.registerCorsConfiguration("/api/v1/client/**", configuration);
//        source.registerCorsConfiguration("/api/v1/clients", configuration);
//        source.registerCorsConfiguration("/api/v1/resourceOwner/**", configuration);
//        source.registerCorsConfiguration("/api/v1/resourceOwners", configuration);
//        source.registerCorsConfiguration("/api/v1/blacklist/**", configuration);
//        source.registerCorsConfiguration("/api/v1/authorize", configuration);
//        source.registerCorsConfiguration("/proxy/blacklist/**", configuration);
//        source.registerCorsConfiguration("/proxy/security/profile", configuration);
//        source.registerCorsConfiguration("/proxy/security/profile/**", configuration);
//        source.registerCorsConfiguration("/proxy/security/profiles", configuration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        /**
         * make sure oauth security check happen after cors filter
         */
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


}
