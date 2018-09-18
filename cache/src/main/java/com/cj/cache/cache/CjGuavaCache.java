package com.cj.cache.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by jsflz on 2018/8/23.
 * Guava cache:
 * No static cache but as a bean to use.
 * No cash loader from datasource. Just get from cache it self.
 *
 * @author cj
 */
@Component
public class CjGuavaCache {

    private static final Logger logger = LoggerFactory.getLogger(CjCaffeineCache.class);

    //No using cache loader when building the cache!
    //Push the cache loader back to the business layer.
    //init the cache
    //Attention: This could not be "static" 'cause this is a bean!
    private Cache<String, String> cjGuavaCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(120, TimeUnit.MINUTES)
            .removalListener((RemovalListener<String, String>) notification ->
                    logger.info("移除了key为:[{}],值为:[{}]的缓存项", notification.getKey(), notification.getValue()))
            .build(); //No cache loader.

    public String getByKey(String key) {
        try {
            return cjGuavaCache.getIfPresent(key);
        } catch (CacheLoader.InvalidCacheLoadException e) {
            logger.error("@@@No value of key:{} found", key);
        } catch (Exception e) {
            logger.error("@@@Exception when load from cache! key:{},exception:{}"
                    , key, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * Like the function: getByFunction() in Caffeine.
     * Functional coding.
     *
     * @param key      key
     * @param callable callable
     * @return value
     * @author cj
     */
    public String getByCallable(String key, Callable callable) {
        try {
            return cjGuavaCache.get(key, callable);
        } catch (ExecutionException e) {
            logger.error("@@@Error when getByCallable,excepiton:{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public synchronized void removeByKey(String key) {
        try {
            cjGuavaCache.invalidate(key);
        } catch (Exception e) {
            logger.error("@@@Error when remove cache.Exception:{}", ExceptionUtils.getStackTrace(e));
        }
    }

    public void clearCache() {
        cjGuavaCache.invalidateAll();
    }

    /**
     * Attention:
     * Usually we should not put a value into cache directly!
     * We should clear the specific value and reload it from datasource next time.
     *
     * @param key   key
     * @param value value
     * @author cj
     */
    public void put(String key, String value) {
        cjGuavaCache.put(key, value);
    }

    public Long getCacheSize() {
        return cjGuavaCache.size();
    }
}
