package com.cj.rateLimit.controller;

import com.cj.rateLimit.service.RateLimitService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jian.Cui on 2018/8/30.
 * 限流的Controller,调用限流service.
 */
@Controller
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RateLimitService rateLimitService;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 测试限流
     * 每秒只处理5个请求,当大于5个时,返回指定的错误信息.客户端可选择重试.
     * 返回的错误信息要提现在接口文档中.
     * Tested
     *
     * @return String
     * @author cj
     */
    @RequestMapping("/access")
    @ResponseBody
    public String access() {
        //尝试获取令牌
        if (rateLimitService.tryAcquire()) {
            logger.info("@@@成功拿到令牌.");
            //模拟业务执行5000毫秒
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error(ExceptionUtils.getStackTrace(e));
                Thread.currentThread().interrupt();
            }
            return "@@@access success [" + sdf.format(new Date()) + "]";
        } else {
            logger.info("@@@发生限流!");
            return "@@@access limit [" + sdf.format(new Date()) + "]";
        }
    }
}
