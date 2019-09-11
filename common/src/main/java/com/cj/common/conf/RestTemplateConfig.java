package com.cj.common.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jian.Cui on 2018/8/30.
 * Configuration for RestTemplate in Spring
 *
 * @author cj
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    /**
     * ConnectTimeout只有在网络正常的情况下才有效，
     * 而当网络不正常时，ReadTimeout才真正的起作用，
     * 即IdIOHandlerStack 里的 WaitFor 是受ReadTimeout限制的，因此，这2个属性应该结合实用。
     *
     * connect timeout 是建立连接的超时时间；
     * read timeout，是传递数据的超时时间。
     *
     * @return ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//ms 设置从主机读取数据超时
        factory.setConnectTimeout(15000);//ms // 设置连接主机超时
        return factory;
    }

}
