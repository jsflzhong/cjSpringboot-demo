package com.cj.httpClient.service.impl;

import java.time.LocalTime;

import com.alibaba.fastjson.JSONObject;
import com.cj.common.entity.ResponseBean;
import com.cj.common.exception.HttpRequestException;
import com.cj.httpClient.service.RetryService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RetryServiceImpl implements RetryService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 再送的发送方, 即调用方.
     * <p>
     * 由于Spring的Retry底层是代理和AOP, 所以调用方和被调用方不能同属于一个类中, 否则无法触发.
     * 同时如果没有引入cglib, 那么也需要"调用方"必须实现了接口,并且该方法是在接口中定义后由实现类来实现的.
     *
     * 注解属性解读:
     * 1.value : 指定要抓的异常.只有在该方法抛出这个异常时才被注解抓到,从而触发retry.
     * 2.maxAttempts: retry的次数. 如果是4, 就是再送3次. 因为本身初始被调用时算一次.
     * 3.delay: 再送的延迟. 这里是1秒.
     * 4.multiplier: 延迟的倍数. 即, 再送第1次是
     *
     */
    @Retryable(value = { HttpRequestException.class }, maxAttempts = 4, backoff = @Backoff(delay = 1000, multiplier = 2))
    @Override
    public ResponseBean<JSONObject> retry(String name) {
        return process(name);
    }

    private ResponseBean<JSONObject> process(String name) {
        String url = String.format("http://localhost:8081/provider/%s", name);
        JSONObject responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, JSONObject.class).getBody();
        } catch (RestClientException e) { //请求超时
            log.error("Request time out!");
            //抛出自定义异常, 被@Retryable捕获. 这里也可以不抛, 可以直接让注解抓RestClientException.
            throw new HttpRequestException("Request time out!" + ExceptionUtils.getStackTrace(e));
        }

        log.info("success，time：{}", LocalTime.now());
        return ResponseBean.success(responseEntity);
    }

    /**
     * 反例测试1:
     * 测试spring的Retry机制.
     *
     * 测试如果调用方的方法,不在父接口中定义,是否会触发代理从而让Retry生效.
     *
     * @param name name
     * @return ResponseBean
     */
    @Retryable(value = { HttpRequestException.class }, maxAttempts = 4, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResponseBean<JSONObject> anti_retry(String name) {
        return process(name);
    }
}
