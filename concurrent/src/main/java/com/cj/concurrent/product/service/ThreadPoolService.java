package com.cj.concurrent.product.service;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RejectedExecutionException;

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

    /**
     * 测试拒绝任务
     * 结论: 拒绝任务的handler可以正常执行, 当task被拒绝时,不会影响主线程的继续往下执行.
     *
     * 基础知识:
     * 拒绝任务有两种情况：1. 线程池已经被关闭；2. 任务队列已满且maximumPoolSizes已满；
     * 无论哪种情况，都会调用RejectedExecutionHandler的rejectedExecution方法。预定义了四种处理策略：
     *
     * AbortPolicy：默认测策略，抛出RejectedExecutionException运行时异常；
     * CallerRunsPolicy：这提供了一个简单的反馈控制机制，可以减慢提交新任务的速度；
     * DiscardPolicy：直接丢弃新提交的任务；
     * DiscardOldestPolicy：如果执行器没有关闭，队列头的任务将会被丢弃，然后执行器重新尝试执行任务（如果失败，则重复这一过程）；
     * 我们可以自己定义RejectedExecutionHandler，以适应特殊的容量和队列策略场景中。
     *
     */
    public void useThreadPool4() {
        for (int i = 0; i <= 6; i++) {
            thirdPartyLogExecutor.execute(new MyTask("task_" + i));
            System.out.println("###main thread, current time:" + i);
        }
    }

    /**
     * 测试拒绝任务
     * 当task被拒绝时, 抓一下看是否会抛出运行时异常:RejectedExecutionException
     * 因为在线程池那边配置的拒绝策略是:RejectedExecutionHandler
     */
    public void useThreadPool5() {
        for (int i = 0; i <= 6; i++) {
            try {
                thirdPartyLogExecutor.execute(new MyTask("task_" + i));
            } catch (RejectedExecutionException e) {
                log.info("@@RejectedExecutionException:" + e); //这里抓不到,因为是主线程.
            } catch (Exception e) {
                log.info("@@Exception:" + e);//这里抓不到,因为是主线程.
            }
           log.info("###main thread, current time:" + i);
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
            } catch (RejectedExecutionException e) {
                log.info("@@In Task: RejectedExecutionException:" + e); //这里也抓不到
            } catch (Exception e) {
                log.info("@@In Task: Exception:" + e);//这里也抓不到
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
