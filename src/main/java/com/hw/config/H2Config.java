package com.hw.config;

import com.hw.clazz.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@Profile("h2")
public class H2Config extends ResourceServerConfigurerAdapter {

    private String ignoredH2Paths = "/h2-console/**";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ArrayList<String> strings1 = new ArrayList<>(Arrays.asList(Constant.ignoreApi));
        strings1.add(ignoredH2Paths);
        String[] arr = new String[strings1.size()];
        http.authorizeRequests()
                .antMatchers(strings1.toArray(arr)).permitAll()
                .and()
                .headers().frameOptions().disable();

    }

}
