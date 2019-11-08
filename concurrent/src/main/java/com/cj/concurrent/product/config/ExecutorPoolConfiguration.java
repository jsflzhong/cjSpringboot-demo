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

    /**
     * 为了测试当最大线程数和任务队列满了后, 对拒绝策略的处理, 这里先设置小的三个数字, 让调用那边触发拒绝策略.
     * 策略:RejectedExecutionHandler
     *
     * 测试结论:
     *  1.满了后, 会触发拒绝handler, 该task会被抛弃.
     *
     */
    @Bean(name = "thirdPartyLogExecutor")
    public Executor getThirdPartyLogExecutor() {
        int corePoolSize = env.getProperty("threadPool.corePoolSize", Integer.class);
        int maximumPoolSize = env.getProperty("threadPool.maximumPoolSize",Integer.class);
        int keepAliveSeconds = env.getProperty("threadPool.keepAliveSeconds",Integer.class);
        return new ThreadPoolExecutor(1, 2, keepAliveSeconds, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2), //todo. 生产环境引用这里时,把上面2个值替换成上面俩变量, 这里的capacity设为空, 默认是Integer.MAX_VALUE.
                (r, executor) -> log.warn("[ThreadConfiguration - thirdPartyLogExecutor] Task Queue is full! New task rejected!"));
    }
}
