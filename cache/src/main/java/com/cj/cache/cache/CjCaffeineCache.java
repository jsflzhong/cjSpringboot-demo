package com.cj.cache.cache;

import com.cj.cache.pojo.DataValue;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by jsflz on 2018/8/22.
 * User Caffeine as cache.
 * Handle the exception in the cache layer.
 *
 * @author cj
 */
@Component
public class CjCaffeineCache {

    private static final Logger logger = LoggerFactory.getLogger(CjCaffeineCache.class);

    //No using cache loader when building the cache!
    //Push the cache loader back to the business layer.
    //init the cache
    //Attention: This could not be "static" 'cause this is a bean!
    Cache<String, DataValue> cache = Caffeine.newBuilder()
            //.expireAfterWrite(1, TimeUnit.MINUTES)
            .expireAfterAccess(120, TimeUnit.MINUTES)
            .maximumSize(100)
            .removalListener((RemovalListener<String, DataValue>) (key, value, cause)
                    -> logger.info("@@@Removing key:{},cause:{}", key, cause))
            .build();

    public DataValue getByKey(String key) {
        try {
            return cache.getIfPresent(key);
        } catch (Exception e) {
            logger.error("@@@Error when get the value form cache,key:{},exception:{}"
                    , key, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public void put(String key, DataValue value) {
        try {
            cache.put(key, value);
        } catch (Exception e) {
            logger.error("@@@Error when put a value into cache,key:{},value:{}, exception:{}"
                    , key, value, ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Get by Function(@FunctionalInterface)_test
     * This is just a function FOR TEST to help me to understand the Functional param.
     * The complete signature of the get() function in Caffeine is : V get(@Nonnull K var1, @Nonnull Function<? super K, ? extends V> var2);
     * Attention: This get() function previously is a : atomic function.
     *
     * @param key @FunctionalInterface
     * @return DataObject
     * @deprecated (This is just a function FOR TEST to help me to understand the Functional param.)
     * @author cj
     */
    @Deprecated
    public DataValue getByFunction_test(String key) {
        //If there is no value in the cache with this key,then return the value of the "Function",
        //Which means return the result of [DataObject.getNewData("testKey")] in this case;
        //Or else return the value in the cache.
        //... ...
        //Is this means give me the result of the key in the cache if there is one,
        //or, i should give a 'dao.selectXXX()' as a Function and the param and the data resource if there isn't ?
        DataValue dataObject = cache.get(key, k -> DataValue.getNewData("testData"));
        return dataObject;
    }

    /**
     * Get by Function(@FunctionalInterface)
     * The complete signature of the get() function in Caffeine is : V get(@Nonnull K var1, @Nonnull Function<? super K, ? extends V> var2);
     * Attention: This get() function previously is a : atomic function.
     * <p>
     * If there is no value in the cache with this key,then return the value of the "Function",
     * Or else return the value in the cache.
     * ... ...
     * Is this means give me the result of the key in the cache if there is one,
     * or, i should give a 'dao.selectXXX()' as a Function and the param and the data resource if there isn't ?
     * <p>
     * Tested:
     * When there is no value with specific key in the cache:
     * Then Get the value from the Functional param,then put the value into the cache and return !
     *
     * @param key @FunctionalInterface
     * @return DataObject
     * @author cj
     */
    public DataValue getByFunction(String key, Function function) {
        try {
            return cache.get(key, function);
        } catch (Exception e) {
            logger.error("@@@Error when get the value form cache with Function,key:{},exception:{}"
                    , key, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public void removeByKey(String key) {
        try {
            cache.invalidate(key);
        } catch (Exception e) {
            logger.error("@@@Error when remove the value form cache,key:{},exception:{}"
                    , key, ExceptionUtils.getStackTrace(e));
        }
    }

    public void removeAll() {
        try {
            cache.invalidateAll();
        } catch (Exception e) {
            logger.error("@@@Error when remove all the elements form cache,exception:{}"
                    , ExceptionUtils.getStackTrace(e));
        }
    }

    public Long getCacheSize() {
        return cache.estimatedSize();
    }

    public Long getHitCount() {
        return cache.stats().hitCount();
    }

    public Long getMissCount() {
        return cache.stats().missCount();
    }


}
