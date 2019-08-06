package com.cj.httpClient.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cj.httpClient.service.RestService;
import com.cj.httpClient.vo.ServiceResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jian.Cui on 2018/8/30.
 * ServiceImpl for RestTemplate in Spring.
 */
@Service
public class RestServiceImpl extends BaseService implements RestService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送get请求(url需要用占位符)
     *
     * @param map 参数map
     * @param url 请求地址，参数拼接在链接中，用通配符代表
     *            示例 :
     *            Map<String, Object> map = new HashMap<String, Object>;
     *            map.put("channel", 1);
     *            map.put("goodsId", 2);
     *            String url = "http://localhost:8083/goods/goodsIndex?channel={channel}&goods_id={goodsId}";
     *            根据实际情况自己添加参数
     * @return ServiceResult
     * @author cj
     */
    @Override
    public ServiceResult parseGetResult(Map<String, Object> map, String url) {
        ServiceResult result;
        try {
            String sResult = restTemplate.getForObject(url, String.class, map);
            JSONObject jResult = JSONObject.parseObject(sResult);
            result = buildSuccessResult(jResult);
        } catch (RestClientException e) {
            result = buildErrorResult("url:" + url + "请求失败");
            logger.info("url:{}, map:{},请求失败,异常:{}", url, map, ExceptionUtils.getStackTrace(e));
        }

        return result;
    }

    /**
     * 发送get请求(推荐)
     *
     * @param map 参数map
     * @param url 请求地址，地址中不需要拼接参数，在方法中处理
     *            示例 :
     *            HashMap<String, Object> map = new HashMap<String, Object>;
     *            map.put("channel", 1);
     *            map.put("goodsId", 2);
     *            String url = "http://localhost:8083/goods/goodsIndex";
     *            根据实际情况自己添加参数
     * @return ServiceResult
     * @author cj
     */
    @Override
    public ServiceResult parseGetResult(HashMap<String, Object> map, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (!CollectionUtils.isEmpty(map)) {
            Set<String> keys = map.keySet();
            sb.append("?");
            for (String string : keys) {
                sb.append(string);
                sb.append("=");
                sb.append(map.get(string).toString());
                sb.append("&");
            }
        }
        String finalUrl = sb.toString();
        if (finalUrl.endsWith("&")) {
            finalUrl = finalUrl.substring(0, finalUrl.length() - 1);
        }
        ServiceResult result = this.parseGetResult(finalUrl);
        return result;
    }

    /**
     * 发送get请求
     *
     * @param url 请求地址
     *            请求为纯字符串，可以是无参数的get请求，也可以是将参数的K V都拼接好传入
     *            示例
     *            1、http://localhost:8083/goods/goodsIndex
     *            2、http://localhost:8083/goods/goodsIndex?channel=1&goods_id=2
     *            以上两种方式都支持
     * @return ServiceResult
     * @author cj
     */
    @Override
    public ServiceResult parseGetResult(String url) {
        ServiceResult result;
        try {
            String sResult = restTemplate.getForObject(url, String.class);
            JSONObject jResult = JSONObject.parseObject(sResult);
            result = buildSuccessResult(jResult);
        } catch (RestClientException e) {
            result = buildErrorResult("url:" + url + "请求失败");
            logger.info("url:{}, 请求失败,异常:{}", url, ExceptionUtils.getStackTrace(e));
        }

        return result;
    }

    /**
     * 发送post请求(推荐)
     *
     * @param map 请求参数集合
     * @param url 请求地址 示例：
     *            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
     *            map.add("channel", 1);
     *            map.add("goods_id", 2);
     *            String url = "http://localhost:8083/goods/goodsIndex";
     *            根据实际情况自己添加参数
     * @return ServiceResult
     * @author cj
     */
    @Override
    public ServiceResult parsePostResult(LinkedMultiValueMap<String, Object> map, String url) {
        ServiceResult result = new ServiceResult();
        try {
            //注意第三参是String,这就要求对方接口的参数是String类型,而不能是Json.(有时间加上Json的)
            String sResult = restTemplate.postForObject(url, map, String.class);
            JSONObject jResult = JSONObject.parseObject(sResult);
            result = buildSuccessResult(jResult);
        } catch (RestClientException e) {
            result = buildErrorResult("url:" + url + "请求失败");
            logger.info("url:{}, map:{},请求失败,异常:{}", url, map, ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    /**
     * 带header的http post请求
     * header中的值可以自己设置
     * HttpEntity中的params,可以被对方接口用中httpEntity.getBody()获取,
     * HttpEntity中的httpHeader,可以被对方接口用中httpEntity.getHeaders()获取(会有5个额外默认自带的header)
     *
     * @return ServiceResult
     * @author cj
     */
    @Override
    public ServiceResult parsePostResultWithHeader(HttpHeaders httpHeader, JSONObject params, String url) {
        ServiceResult result;
        try {
            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, httpHeader);
            ResponseEntity exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            JSONObject jResult = JSONObject.parseObject(exchange.getBody().toString());
            result = buildSuccessResult(jResult);
        } catch (Exception e) {
            logger.info("url:{}, map:{},请求失败,异常:{}", url, params, ExceptionUtils.getStackTrace(e));
            result = buildErrorResult("url:" + url + "请求失败");
        }
        return result;
    }
}
