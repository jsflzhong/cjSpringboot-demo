package com.cj.spring.retry.controller;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSONObject;
import com.cj.common.controller.BaseController;
import com.cj.common.entity.ResponseBean;
import com.cj.spring.retry.service.RetryService;
import com.cj.spring.retry.service.RetryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试spring的Retry机制.
 *
 * 概念定义:
 * 调用方: 带@Retryable注解的方法.
 * 被调用方: 被上述方法给调用的方法.
 *
 * 需要引入POM.
 *
 */
@RestController
public class RetryController extends BaseController {

    Logger log = LoggerFactory.getLogger(RetryController.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RetryService retryService;

    private static AtomicLong helloTimes = new AtomicLong();

    /**
     * 测试入口.
     * 调用方接口.
     * 测试spring的Retry机制.
     * 1.需要在配置类上开启retry.
     *  com.cj.spring.retry.config.RetryConfig
     * 2.由于Spring的Retry底层是代理和AOP, 所以调用方和被调用方不能同属于一个类中, 否则无法触发.
     *  所以把调用的动作, 拆到Service层了, 与下面的被调用的接口分开.
     * 3.如果没有引入cglib, 那么也需要"调用方"必须实现了接口,并且该方法是在接口中定义后由实现类来实现的. JDK代理.
     *  com.cj.httpClient.service.RetryService#retry(java.lang.String)
     * 4.Retry针对的是异常的捕获, 所以该功能不限定于HTTP接口的调用, 即使是普通的方法调用,也可以使用.
     *
     *  测试结果:
     *      retry成功.
     * @param name name
     * @return Object
     */
    @GetMapping("/caller/{name}")
    public Object caller(@PathVariable("name") String name) {
        retryService.retry(name);
        return new ResponseBean<>();
    }

    /**
     * *******被调用方.
     * 测试spring的Retry机制.
     * RestTemplate那边设置的超时时间是5秒,所以这里卡主6秒造成超时.
     *
     * @param name name
     * @return Object
     */
    @GetMapping("/provider/{name}")
    public Object provider(@PathVariable("name") String name) {
        long times = helloTimes.incrementAndGet();
        log.info("@@@provider接口被调用!本次是:{}", times);
        //平均每四次会有三次由于阻塞而返回超时, 只有一次会成功.
        //if (times % 4 != 0) {
            log.error("@@@进入阻塞, restTemplate的超时时间是5秒. time：{}", LocalTime.now());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        //}

        return new JSONObject().put("key1",name);
    }

    /**
     * 反例, 测试入口1:
     * 调用方.
     * 测试:
     *  测试如果调用方的方法,不在父接口中定义,是否会触发代理从而让Retry生效.
     * 结果:
     *  测试成功, 调用的方法不在父接口中, 所以Retry底层的AOP无法代理,也就无法retry了.
     *
     * @param name
     * @return
     */
    @GetMapping("/anti_caller/{name}")
    public Object anti_caller(@PathVariable("name") String name) {
        ResponseBean<JSONObject> responseBean = new RetryServiceImpl().anti_retry(name);
        return responseBean;
    }
}
