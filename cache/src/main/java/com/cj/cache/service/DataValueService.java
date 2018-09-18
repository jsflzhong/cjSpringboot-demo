package com.cj.cache.service;


import com.cj.cache.pojo.DataValue;

/**
 * Created by jsflz on 2018/8/22.
 */
public interface DataValueService {

    DataValue getDataValueById_test(Integer id);

    String getStringByKey_test(String key);
}
