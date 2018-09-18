package com.cj.cache.controller;

import com.cj.cache.cache.CjGuavaCache;
import com.cj.cache.service.DataValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jsflz on 2018/8/22.
 * Tested for guava cache.
 *
 * @author cj
 */
@Controller
@RequestMapping("/guavaCache")
public class GuavaCacheController {

    @Autowired
    CjGuavaCache cjGuavaCache;
    @Autowired
    private DataValueService dataValueService;

    /**
     * Get value by specific key.
     * Tested
     *
     * @param key key
     * @return value
     * @author cj
     */
    @RequestMapping("/getByKey")
    @ResponseBody
    public String getByKey(String key) {
        String value = cjGuavaCache.getByKey(key);
        //if(Strings.isNullOrEmpty(value)) //Get value from db...
        return value;
    }

    /**
     * Put value and key into cache.
     * Tested
     *
     * @param key   key
     * @param value value
     * @return success
     * @author cj
     */
    @RequestMapping("/put")
    @ResponseBody
    public Object put(String key, String value) {
        cjGuavaCache.put(key, value);
        return "success";
    }

    /**
     * Get value by specific key or function.
     * If the value of specific key is not existed,
     * then use the function to get the value and put the value into the cache.
     * Tested
     *
     * @param key key
     * @return value
     * @author cj
     */
    @RequestMapping("/getByFunction")
    @ResponseBody
    public Object getByFunction(String key) {
        return cjGuavaCache.getByCallable(key,
                () -> dataValueService.getStringByKey_test(key));
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
        cjGuavaCache.removeByKey(key);
        return "success";
    }

    /**
     * Get the size of the cache.
     * Tested
     *
     * @return size
     * @author cj
     */
    @RequestMapping("/getCacheSize")
    @ResponseBody
    public Object getCacheSize() {
        return cjGuavaCache.getCacheSize();
    }


}
