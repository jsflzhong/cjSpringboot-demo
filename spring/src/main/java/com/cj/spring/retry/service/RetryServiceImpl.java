package com.cj.spring.retry.service;

import java.time.LocalTime;

import com.alibaba.fastjson.JSONObject;
import com.cj.common.entity.ResponseBean;
import com.cj.common.exception.HttpRequestException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
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
     * 4.multiplier: 延迟的倍数. 即, 这里设置为2, 意味着: 再送第1次是1秒后,第2次是2秒后,第三次是4秒后.
     *
     */
    @Retryable(
            value = { HttpRequestException.class },
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    @Override
    public ResponseBean<JSONObject> retry(String name) {
        return process(name);
    }

    /**
     * 重试到次数依然失败之后的回调方法.
     *
     * 注意,坑比较多.
     *
     * 注意,该方法的形参的类型,需要与上面retry方法的注解抓的异常类型一致才可以!
     *
     * (本条作废)注意,由于底层是AOP,所以该方法不能与@Retry方法在同一个类中,否则无法生效!
     *
     * (本条作废)这里这个是反例, 正例见:com.cj.spring.retry.service.RecoverService#recocer(com.cj.common.exception.HttpRequestException)
     *
     * 注意,虽然底层是AOP,但是经过测试,这里是可以被上面的方法触发的.
     *
     * 注意,如果在其他类中定义的recover,反倒是无法被这个类中上面的retry方法调用!
     *
     * 注意,该recover方法不用在上层接口中定义.
     *
     * 注意,该recover方法的返回值,必须与上面retry方法的返回值一样!否则无法被调用.(不用为void)
     *
     * @param e e
     */
    @Recover
    @Deprecated //正例了
    public ResponseBean<JSONObject> recocer(HttpRequestException e) {
        log.info("@@@虽然该方法与上面的retry方法在同一类中,但是这个方法还是会被上面的retry触发.");
        return new ResponseBean<>();
    }

    private ResponseBean<JSONObject> process(String name) {
        String url = String.format("http://localhost:8013/provider/%s", name);
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
     * 由于controller是直接new这个对象然后调用这个方法,所以RestTemplate这个bean不会被这里用到.所以在该方法中重新new一个RestTemplate,或从IOC中拿.
     *
     * @param name name
     * @return ResponseBean
     */
    @Retryable(value = { HttpRequestException.class }, maxAttempts = 4, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResponseBean<JSONObject> anti_retry(String name) {
        return anti_process(name);
    }

    private ResponseBean<JSONObject> anti_process(String name) {
        String url = String.format("http://localhost:8013/provider/%s", name);
        JSONObject responseEntity;
        try {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setReadTimeout(5000);//ms 设置从主机读取数据超时
            factory.setConnectTimeout(15000);//ms // 设置连接主机超时
            RestTemplate rt = new RestTemplate(factory);
            responseEntity = rt.getForEntity(url, JSONObject.class).getBody();
        } catch (RestClientException e) { //请求超时
            log.error("Request time out!");
            //抛出自定义异常, 被@Retryable捕获. 这里也可以不抛, 可以直接让注解抓RestClientException.
            throw new HttpRequestException("Request time out!" + ExceptionUtils.getStackTrace(e));
        }

        log.info("success，time：{}", LocalTime.now());
        return ResponseBean.success(responseEntity);
    }
}
