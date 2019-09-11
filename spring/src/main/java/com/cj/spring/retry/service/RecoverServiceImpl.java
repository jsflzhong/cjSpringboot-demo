package com.cj.spring.retry.service;

import com.alibaba.fastjson.JSONObject;
import com.cj.common.entity.ResponseBean;
import com.cj.common.exception.HttpRequestException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RecoverServiceImpl implements RecoverService{

    /**
     * 重试到次数依然失败之后的回调方法.
     *
     * 注意! 该方法目前无法被其他类中的retry所触发!
     * 生效的recover见:com.cj.spring.retry.service.RetryServiceImpl#recocer(com.cj.common.exception.HttpRequestException)
     *
     * 注意,该方法的形参的类型,需要与上面retry方法的注解抓的异常类型一致才可以!
     *
     * 注意,由于底层是AOP,所以该方法不能与@Retry方法在同一个类中,否则无法生效!
     *
     * 注意,该recover方法的返回值,需要与retry那边方法的返回值一样! 否则无法被调用!
     * @param e
     */
    @Override
    @Recover
    public ResponseBean<JSONObject> recocer(HttpRequestException e) {
        log.info("@@@Real recover is running, e:{}", ExceptionUtils.getStackTrace(e));
        return new ResponseBean<>();
    }
}
