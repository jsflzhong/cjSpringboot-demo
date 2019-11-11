package com.cj.concurrent.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionalService {

    /**
     * 测试, 当被子线程运行的这个方法中出现异常时,是否会影响到主线程.
     */
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test1() {
        log.info("@@子线程开始运行,马上要引发异常...");
        int a = 1/0;
        log.info("@@子线程结束运行");

    }
}
