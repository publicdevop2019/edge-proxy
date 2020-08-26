package com.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

@Configuration
public class BeanFactory {
    @Bean
    public AntPathMatcher getAntPathMatcher() {
        return new AntPathMatcher();
    }
}
