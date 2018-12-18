package com.cj.camel.router;

import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DoubleRouters_B extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(DoubleRouters_B.class);

    /**
     * Tested
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {
        logger.info("@@@DoubleRouters_B is running...");

        //from("direct:doubleRouters_B")
        from("direct:dB") //实测这个value可以随意写,只要在生产路由那边能匹配既可.
                .to("log:DoubleRouters_B?showExchangeId=true");

        logger.info("@@@DoubleRouters_B is finishing...");
    }
}
