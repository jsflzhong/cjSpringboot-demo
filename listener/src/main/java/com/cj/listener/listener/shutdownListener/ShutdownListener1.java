package com.cj.listener.listener.shutdownListener;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 随容器关闭而执行的listener.
 * 1.需要这个类.
 * 2.需要配置这个类成为一个bean,同时需要把它注册进tomcat.见:
 *  1>.com.cj.listener.config.ConfigClass#shutdownListener1() //已弃用,换成了这个用@Component注解.
 *  2>.com.cj.listener.config.ConfigClass#webServerFactory(com.cj.listener.listener.shutdownListener.ShutdownListener1)
 */
@Slf4j
@Component
public class ShutdownListener1 implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private static final int TIMEOUT = 30;

    private volatile Connector connector;

    /**
     * TomcatConnectorCustomizer的重载方法
     * @param connector connector
     */
    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    /**
     * ApplicationListener的重载方法
     *
     * ExecutorService  的 shutDown 和 shutDownNow 的区别:
     * shutDown:不再接受新的线程，并且等待之前提交的线程都执行完在关闭，
     * shutDownNow: 直接关闭活跃状态的所有的线程 ， 并返回等待中的线程
     *
     * @param contextClosedEvent contextClosedEvent
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        //优雅的关闭线程池
        closeExecutor();
    }

    /**
     * 在关闭容器时,先关闭线程池对外接受请求, 再等30秒让队列中的任务处理完毕,最后关闭线程池.
     */
    private void closeExecutor() {
        //暂停接受外部的所有新的请求
        this.connector.pause();
        //获取Connector使用的线程池
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if(executor instanceof ThreadPoolExecutor) {
            try {
                log.warn("@@@WEB应用准备关闭...");
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                log.warn("@@@WEB开始执行关闭...");
                threadPoolExecutor.shutdown();
                //阻塞等待30秒关掉线程池，返回true表示已经关闭。和shutdown不同，它可以接收外部任务，并且还阻塞.
                if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    //30秒后强制关闭线程池.
                    log.warn("@@@WEB 应用等待关闭超过最大时长 " + TIMEOUT + " 秒，将进行强制关闭!");
                    threadPoolExecutor.shutdownNow();

                    if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                        log.error("@@@WEB 应用关闭失败!");
                    }
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
