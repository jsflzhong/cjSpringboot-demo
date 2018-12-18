package com.cj.camel.router;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

/**
 * 测试request由路由A转给路由B.
 * Endpoint Direct
 */
@Component
public class DoubleRouters_A extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(DoubleRouters_A.class);

    @Autowired
    private CsdnRouter1 csdnRouter1;

    /**
     * Tested
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {
        logger.info("@@@DoubleRouters_A is running...");

        from("jetty://http://0.0.0.0:8085/doubleRouters_A")
                .process(exchange -> {
                    Object obj = Optional.ofNullable(exchange)
                            .map(Exchange::getIn)
                            .map(Message::getBody)
                            .orElseThrow(() -> new RuntimeException("Null body!"));

                    String inputData;
                    try (InputStream in = (InputStream) obj) {
                        inputData = csdnRouter1.analysisMessage(in);
                    }

                    exchange.getOut().setBody(inputData);
                })
                //.to("direct:doubleRouters_B")
                .to("direct:dB") //实测这个value可以随意写,只要在消费路由那边能匹配既可.
                .to("log:DoubleRouters_A?showExchangeId=true");

        logger.info("@@@DoubleRouters_A is finishing...");
    }
}
