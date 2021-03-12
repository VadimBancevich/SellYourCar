package com.vb.services.cache.configuration;

import com.google.common.cache.CacheBuilder;
import com.vb.services.cache.configurators.CacheConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    @Autowired
    public CacheManager cacheManager(List<CacheConfigurator> configurators) {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                boolean cacheHasConfigurators =
                        configurators.stream().anyMatch(cacheConfigurator -> cacheConfigurator.isCacheNamePresent(name));
                if (cacheHasConfigurators) {
                    CacheBuilder<Object, Object> cb = CacheBuilder.newBuilder();
                    configurators.forEach(cacheConfigurator -> cacheConfigurator.configure(name, cb));
                    return new ConcurrentMapCache(name, cb.build().asMap(), false);
                } else {
                    return super.createConcurrentMapCache(name);
                }
            }
        };
    }

}
