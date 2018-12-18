package com.cj.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

/**
 * Get request from routeBuilder in HTTP protocol.
 */
//DO NOT forget the bean annotation!!
@Component
public class ContentBasedProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(ContentBasedProcessor.class);

    /**
     * processor
     * Test: http://localhost:8082/myapp/myservice?test1=param1&test2=param2
     * 1.Output back to customer.
     * 2.Get params from URI.
     * Tested
     *
     * @param exchange exchange
     * @throws Exception Exception
     * @author cj
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("@@@ContentBasedProcessor is running......");

        //Get parameters from uri.
        Object conditionId = Optional.ofNullable(exchange)
                .map(Exchange::getIn)
                .map(message -> message.getHeader("conditionId"))
                .orElseThrow(() -> new RuntimeException("conditionId is null!"));

        logger.info("@@@The conditionId from URI is:{} ", conditionId);

        //Sent body back to customer as response.
        exchange.getOut().setBody("<html><body>ContentBasedProcessor,param: " + conditionId + "</body></html>");
        //在processor这里设置header,可以在router那边的下面读出来.
        exchange.getOut().setHeader("cId",conditionId);

        logger.info("@@@ContentBasedProcessor is finishing......");
    }






    public static void main(String[] args) {
        HashMap<String, String> map = null;

        Optional.ofNullable(map)
                .map(o -> o.get("key1"))
                .orElseThrow(() -> new RuntimeException("Map is empty!")); //可以抛出自定义异常.

    }
}
