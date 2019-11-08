package com.cj.concurrent.product.config;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.cj.concurrent.product.executor.CustomizedThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Configuration
@PropertySource("classpath:config-thread.properties") //necessary.
@Slf4j
public class ExecutorPoolConfiguration {

    @Autowired
    private Environment env;

    @Bean("coeExecutor")
    public Executor getExecutor() {
        int corePoolSize = env.getProperty("threadPool.corePoolSize", Integer.class);
        int maximumPoolSize = env.getProperty("threadPool.maximumPoolSize",Integer.class);
        int keepAliveSeconds = env.getProperty("threadPool.keepAliveSeconds",Integer.class);
        return new CustomizedThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveSeconds,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Bean(name = "thirdPartyLogExecutor")
    public Executor getThirdPartyLogExecutor() {
        int corePoolSize = env.getProperty("threadPool.corePoolSize", Integer.class);
        int maximumPoolSize = env.getProperty("threadPool.maximumPoolSize",Integer.class);
        int keepAliveSeconds = env.getProperty("threadPool.keepAliveSeconds",Integer.class);
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                (r, executor) -> log.warn("[ThreadConfiguration - thirdPartyLogExecutor] Task Queue is full! New task rejected!"));
    }
}
