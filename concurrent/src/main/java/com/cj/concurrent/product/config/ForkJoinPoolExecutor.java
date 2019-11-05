package com.cj.concurrent.product.config;

import java.util.concurrent.ForkJoinPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ForkJoinPoolFactoryBean;

/**
 * Remember Fork join is good to be used in "分而治之", if not, it's better to use ExecutorService.
 * @return
 */
@Configuration
@PropertySource("classpath:config-thread.properties")
public class ForkJoinPoolExecutor {

    @Bean
    public ForkJoinPoolFactoryBean partnerTimeoutServiceForkJoinPool() {
        ForkJoinPoolFactoryBean poolFactoryBean = new ForkJoinPoolFactoryBean();
        poolFactoryBean.setParallelism(10);
        return poolFactoryBean;
    }

    @Bean
    public ForkJoinPoolFactoryBean resendHomerResultMessageForkJoinPool() {
        ForkJoinPoolFactoryBean poolFactoryBean = new ForkJoinPoolFactoryBean();
        poolFactoryBean.setParallelism(10);
        return poolFactoryBean;
    }
}
