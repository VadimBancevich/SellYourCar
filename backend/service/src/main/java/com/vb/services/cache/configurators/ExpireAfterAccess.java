package com.vb.services.cache.configurators;

import com.google.common.cache.CacheBuilder;
import com.vb.services.cache.configurators.util.TimeUtils;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("cache.expire")
public class ExpireAfterAccess implements CacheConfigurator {

    @Setter
    private Map<String, String> afterAccess = new HashMap<>();

    @Override
    public void configure(String cacheName, CacheBuilder<Object, Object> cb) {
        if (afterAccess.containsKey(cacheName)) {
            Duration duration = TimeUtils.determineDuration(afterAccess.get(cacheName));
            cb.expireAfterAccess(duration);
        }
    }

    @Override
    public boolean isCacheNamePresent(String cacheName) {
        return afterAccess.containsKey(cacheName);
    }

}
