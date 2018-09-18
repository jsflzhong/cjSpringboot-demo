package com.cj.httpClient.service;

import com.alibaba.fastjson.JSONObject;
import com.cj.httpClient.vo.ServiceResult;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jian.Cui on 2018/8/30.
 * Service for for AsyncRestTemplate in Spring
 */
public interface AsyncRestService {

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
     * @param url
     * @return
     */
    ServiceResult parseGetResult(String url);

    /**
     * get请求
     *
     * @param map
     * @param url
     * @return
     */
    ServiceResult parseGetResult(HashMap<String, Object> map, String url);

    /**
     * post请求
     *
     * @param map
     * @param url
     * @return
     */
    ServiceResult parsePostResult(LinkedMultiValueMap<String, Object> map, String url);

    /**
     * 带header的http请求
     *
     * @param headers
     * @param params
     * @param url
     * @return
     */
    ServiceResult parsePostResultWithHeader(HttpHeaders headers, JSONObject params, String url);
}
