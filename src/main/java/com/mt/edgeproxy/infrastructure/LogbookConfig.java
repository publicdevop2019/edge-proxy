package com.mt.edgeproxy.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilter;

import static java.util.Collections.singleton;
import static org.zalando.logbook.BodyFilter.merge;
import static org.zalando.logbook.BodyFilters.defaultValue;
import static org.zalando.logbook.json.JsonBodyFilters.replaceJsonStringProperty;

@Configuration
public class LogbookConfig {
    @Bean
    public BodyFilter bodyFilter() {
        BodyFilter merge = merge(replaceJsonStringProperty(singleton("refresh_token"), "XXX"), replaceJsonStringProperty(singleton("password"), "XXX"));
        BodyFilter merge2 = merge(merge, replaceJsonStringProperty(singleton("currentPwd"), "XXX"));
        BodyFilter merge3 = merge(merge2, replaceJsonStringProperty(singleton("pwd"), "XXX"));
        return merge(defaultValue(), merge3);
    }
}
