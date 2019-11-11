package com.cj.concurrent.product.controller;

import com.cj.concurrent.product.service.ThreadPoolServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/threadPool")
public class ThreadPoolController {

    @Autowired
    private ThreadPoolServiceI threadPoolService;

    @GetMapping("/test1")
    public Object test1() {
        threadPoolService.useThreadPool1();
        return "1";
    }

    @GetMapping("/test2")
    public Object test2() {
        threadPoolService.useThreadPool2();
        return "1";
    }
    @GetMapping("/test3")
    public Object test3() {
        threadPoolService.useThreadPool3();
        return "1";
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
    @GetMapping("/test4")
    public Object test4() {
        threadPoolService.useThreadPool4();
        return "1";
    }

    /**
     * 测试拒绝任务
     * 当task被拒绝时, 抓一下看是否会抛出运行时异常:RejectedExecutionException
     * 因为在线程池那边配置的拒绝策略是:RejectedExecutionHandler
     */
    @GetMapping("/test5")
    public Object test5() {
        threadPoolService.useThreadPool5();
        return "1";
    }

    /**
     * 测试拒绝任务
     * 当task被拒绝时, 抓一下看是否会抛出运行时异常:RejectedExecutionException
     * 因为在线程池那边配置的拒绝策略是:RejectedExecutionHandler
     */
    @GetMapping("/test6")
    public Object test6() throws InterruptedException {
        threadPoolService.useThreadPool6();
        return "1";
    }


}
