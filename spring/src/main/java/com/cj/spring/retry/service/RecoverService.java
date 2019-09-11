package com.cj.spring.retry.service;

import com.alibaba.fastjson.JSONObject;
import com.cj.common.entity.ResponseBean;
import com.cj.common.exception.HttpRequestException;

public interface RecoverService {

    ResponseBean<JSONObject> recocer(HttpRequestException e);
}
