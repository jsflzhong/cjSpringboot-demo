package com.cj.camel.router;

import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 只是一个下游的消费者路由. 用来测试从上游的router路由过来.
 */
@Component
public class ConsumerRouters_B extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(ConsumerRouters_B.class);

    /**
     * Tested
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {
        logger.info("@@@ConsumerRouters_B is running...");

        //from("direct:doubleRouters_B")
        from("direct:consumerB") //实测这个value可以随意写,只要在生产路由那边能匹配既可.
                .to("log:ConsumerRouters_B?showExchangeId=true");

        logger.info("@@@ConsumerRouters_B is finishing...");
    }
}
