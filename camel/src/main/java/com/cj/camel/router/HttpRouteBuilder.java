package com.cj.camel.router;

import com.cj.camel.processor.MyFoodProcessor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//DO NOT forget the bean annotation!!

/**
 * 1.HttpRouter
 */
@Component
public class HttpRouteBuilder extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(HttpRouteBuilder.class);

    @Override
    public void configure() throws Exception {
        logger.info("@@@HttpRouteBuilder is working...");
        //Attention: This port can NOT be same as the one configure in the properties file as the server port!
        //Otherwise the server will shutdown automatically!
        from("jetty:http://localhost:8082/myapp/myservice")//如果ip_address 指定为0.0.0.0，就可以监听全网
                .process(new MyFoodProcessor());

    }
}
