//package com.mt.edgeproxy.infrastructure;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
///**
// * zuul proxy resource server can not set resourceId,
// * overwrite default resource id also required
// * if set then forwarded request will be force to check against this resource
// */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
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
//}
