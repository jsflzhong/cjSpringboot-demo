package com.cj.cache.AnnotationGuava.config;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnotationGuavaConfig {

   /* @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(
                CacheBuilder.newBuilder().
                        expireAfterWrite(10, TimeUnit.SECONDS).
                        maximumSize(1000));
        return cacheManager;
    }*/
}
