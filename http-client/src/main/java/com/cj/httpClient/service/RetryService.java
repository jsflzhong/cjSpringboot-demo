package com.cj.httpClient.service;

import com.alibaba.fastjson.JSONObject;
import com.cj.common.entity.ResponseBean;

public interface RetryService {

    /**
     * 调用方的方法必须在上层接口中定义.(JDK代理)
     * @param name
     * @return
     */
    ResponseBean<JSONObject> retry(String name);
}
