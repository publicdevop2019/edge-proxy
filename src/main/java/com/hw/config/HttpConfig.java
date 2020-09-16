package com.hw.config;

import com.hw.config.filter.CachedETagHeaderFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class HttpConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("lastupdateat");
        configuration.addAllowedHeader("uuid");
        configuration.addAllowedHeader("changeId");
        configuration.addExposedHeader("location");
        configuration.addExposedHeader("lastupdateat");
        configuration.addExposedHeader("uuid");
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
        configuration.setMaxAge(86400L); // permission may be cached for 1 day (86400 seconds)
        source.registerCorsConfiguration("/**/**/**", configuration);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        /**
         * make sure oauth security check happen after cors filter
         */
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowETagHeaderFilter(CachedETagHeaderFilter filter) {
        FilterRegistrationBean<ShallowEtagHeaderFilter> bean
                = new FilterRegistrationBean<>(filter);
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return bean;
    }

}
