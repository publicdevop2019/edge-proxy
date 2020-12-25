package com.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.mt","com.hw"})
@EnableJpaRepositories(basePackages = {"com.mt","com.hw"})
@EntityScan(basePackages = {"com.mt","com.hw"})
public class Proxy {

    public static void main(String[] args) {
        SpringApplication.run(Proxy.class, args);
    }

}
