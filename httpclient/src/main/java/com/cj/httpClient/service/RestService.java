package com.cj.httpClient.service;

import com.alibaba.fastjson.JSONObject;
import com.cj.httpClient.vo.ServiceResult;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jian.Cui on 2018/8/30.
 * Service for RestTemplate in Spring.
 */
public interface RestService {

    /**
     * get请求
     *
     * @param map
     * @param url
     * @return
     */
    ServiceResult parseGetResult(Map<String, Object> map, String url);

    /**
     * get请求
     *
     * @param map
     * @param url
     * @return
     */
    ServiceResult parseGetResult(HashMap<String, Object> map, String url);

    /**
     * 无参或已拼接参数get请求
     *
     * @param url
     * @return
     */
    ServiceResult parseGetResult(String url);

    /**
     * post请求
     *
     * @param map
     * @param url
     * @return
     */
    ServiceResult parsePostResult(LinkedMultiValueMap<String, Object> map, String url);

    /**
     * 带有请求头的post请求
     *
     * @param httpHeader
     * @param params
     * @param url
     * @return
     */
    ServiceResult parsePostResultWithHeader(HttpHeaders httpHeader, JSONObject params, String url);
}
