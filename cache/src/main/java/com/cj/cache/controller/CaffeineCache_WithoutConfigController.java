package com.cj.cache.controller;

import com.cj.cache.pojo.DataValue;
import com.cj.cache.service.DataValueService;
import com.cj.cache.vo.BusinessResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by jsflz on 2018/8/22.
 * Tested
 *
 * @author cj
 */
@Controller
@RequestMapping("/caffeineCacheNoConfig")
public class CaffeineCache_WithoutConfigController {

    Logger logger = LoggerFactory.getLogger(CaffeineCache_WithoutConfigController.class);

    @Autowired
    private Cache<String, DataValue> cjCaffeineCache_noConfig;
    @Autowired
    private DataValueService dataValueService;

    @Bean(name = "cjCaffeineCache_noConfig")
    private Cache<String, DataValue> initMatrixConfigCache() {
        return Caffeine.newBuilder().maximumSize(700)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    /**
     * Get value by specific key.
     * Tested
     *
     * @param key key
     * @return value
     * @author cj
     */
    @RequestMapping("/get")
    @ResponseBody
    public DataValue get(String key) {
        logger.info("@@@get...[{}]", key);

        return cjCaffeineCache_noConfig.get(key, o -> {
            logger.info("@@@Get value from db!");
            return dataValueService.getStringByKey_test2(key);
        });
    }

    /**
     * Put value and key into cache.
     * Tested
     *
     * @param key key
     * @return success
     * @author cj
     */
    @RequestMapping("/put")
    @ResponseBody
    public BusinessResponse put(String key) {
        logger.info("@@@put...[{}]", key);

        return dataValueService.insert(new DataValue("test1"))
                .ifSuccess(cjCaffeineCache_noConfig::invalidateAll);
    }

    /**
     * Remove the value of specific key.
     * Tested
     *
     * @param key key
     * @return value
     * @author cj
     */
    @RequestMapping("/removeByKey")
    @ResponseBody
    public Object removeByKey(String key) {
        cjCaffeineCache_noConfig.invalidate(key);
        return "success";
    }

    /**
     * Get the cache.
     * Tested
     *
     * @return size
     * @author cj
     */
    @RequestMapping("/getCache")
    @ResponseBody
    public Object getCache() {
        return cjCaffeineCache_noConfig.asMap();
        //return cjCaffeineCache_noConfig.estimatedSize();
    }


}
