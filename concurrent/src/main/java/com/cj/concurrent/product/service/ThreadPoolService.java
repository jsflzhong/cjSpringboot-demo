package com.cj.concurrent.product.service;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ThreadPoolService {

    @Autowired
    private Executor coeExecutor;
    @Autowired
    private ForkJoinPool partnerTimeoutServiceForkJoinPool;
    @Autowired
    private ForkJoinPool resendHomerResultMessageForkJoinPool;
    @Autowired
    private Executor thirdPartyLogExecutor;

    public void useThreadPool1() {
        log.info("@@@Thread1-1:{}", Thread.currentThread().getName());
        coeExecutor.execute(() ->
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

    public void useThreadPool4() {
        for (int i = 0; i <= 6; i++) {
            thirdPartyLogExecutor.execute(new MyTask("task_" + i));
        }
    }

    static class MyTask implements Runnable {
        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println("@@@Current task:" + this.toString() + " is sleeping... thread:" + Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }
}
