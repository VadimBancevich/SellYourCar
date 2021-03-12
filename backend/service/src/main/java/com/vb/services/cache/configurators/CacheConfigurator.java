package com.vb.services.cache.configurators;

import com.google.common.cache.CacheBuilder;

public interface CacheConfigurator {

    void configure(String cacheName, CacheBuilder<Object, Object> cb);

    boolean isCacheNamePresent(String cacheName);

}
