package com.cj.concurrent.product.service;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ForkJoinPoolFactoryBean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ThreadPoolService {

    @Autowired
    private Executor executor;
    @Autowired
    private ForkJoinPool partnerTimeoutServiceForkJoinPool;
    @Autowired
    private ForkJoinPool resendHomerResultMessageForkJoinPool;

    public void useThreadPool1() {
        log.info("@@@Thread1-1:{}", Thread.currentThread().getName());
        executor.execute(() ->
                log.info("@@@Thread2:{}", Thread.currentThread().getName())
        );
        log.info("@@@Thread1-2:{}", Thread.currentThread().getName());
    }

    public void useThreadPool2() {
        log.info("@@@Thread1-1:{}", Thread.currentThread().getName());
        partnerTimeoutServiceForkJoinPool.submit(() ->
                log.info("@@@Thread2:{}", Thread.currentThread().getName())
        );
        log.info("@@@Thread1-2:{}", Thread.currentThread().getName());
    }

    public void useThreadPool3() {
        log.info("@@@Thread1-1:{}", Thread.currentThread().getName());
        resendHomerResultMessageForkJoinPool.submit(() ->
                log.info("@@@Thread2:{}", Thread.currentThread().getName())
        );
        log.info("@@@Thread1-2:{}", Thread.currentThread().getName());
    }
}
