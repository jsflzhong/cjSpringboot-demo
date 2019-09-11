package com.cj.spring.common.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

/**
 * Created by Jian.Cui on 2018/8/30.
 * Configuration for AsyncRestTemplate in Spring
 */
@Configuration
public class AsyncRestTemplateConfig {

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }

    @Bean
    public AsyncClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setReadTimeout(5000);
        return factory;

    }
}
