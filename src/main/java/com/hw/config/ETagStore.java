package com.hw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Component
public class ETagStore {
    @Autowired
    StringRedisTemplate redisTemplate;
    private static final String E_TAG = "ETag";

    public void setETags(String uri, String query, String etageValue) {
        String resourceName = getResourceName(uri);
        redisTemplate.opsForValue().set(E_TAG + "-" + resourceName + ":" + query.hashCode(), etageValue);
    }

    public String getETags(String uri, String query) {
        String resourceName = getResourceName(uri);
        return redisTemplate.opsForValue().get(E_TAG + "-" + resourceName + ":" + query.hashCode());
    }

    public void clearResourceETag(String uri) {
        String resourceName = getResourceName(uri);
        Set<String> keys = redisTemplate.keys(E_TAG + "-" + resourceName + ":" + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    public static String getResourceName(String uri) {
        String[] split = uri.split("/");
        return split[1] + "/" + split[2];
    }
}
