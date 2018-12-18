package com.cj.camel.router;

import com.cj.camel.processor.ConsumerProcessor_A;
import org.apache.camel.model.MulticastDefinition;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 静态路由
 * 使用"multicast" 处理 "Static Recipient List".
 * 即: 发送给多个接收者(代码中写死下游的消费路由.如果需要动态的分配,见:RecipientRouter)
 * 使用multicast将原始的Exchange复制了多份，分别传送给multicast中的两个接收者(俩log)，
 * 并且为了保证两个接收者的处理过程是"并行的"，我们还专门为multicast设置了一个线程池（不设置的话Camel将自行设置）。
 * 在multicast路由定义之后还设置了一个OtherProcessor处理器,它不是并发执行的一部分,而是之后被执行.
 */
@Component
public class MulticastRouter extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        //准备一个fixed线程池.
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        MulticastDefinition multicast = from("jetty:http://0.0.0.0:8087/multicastRouter").multicast();

        //multicast 中的消息路由可以顺序执行也可以并发执行
        multicast.setParallelProcessing(true);
        //为并发执行设置一个独立的线程池
        multicast.setExecutorService(executorService);

        // 注意，multicast中各路由路径的Excahnge都是基于上一路由元素的Exchange复制而来
        // 无论前者Excahnge中的Pattern如何设置，其处理结果都不会反映在最初的Exchange对象中
        multicast.to(
                "log:test1?showExchangeId=true",
                "log:test2?showExchangeId=true")
        // 一定要使用end，否则下面的OtherProcessor会被做为上面multicast中的一个分支路由而已.
        .end()
        // 为了让下面的processor中能拿到header里的属性,这里做一个.
        .setHeader("cId",() -> "cid")
        // 这里与上面的并行没关系,而是最后肯定会被顺序执行到.
        .process(new ConsumerProcessor_A());
    }
}



