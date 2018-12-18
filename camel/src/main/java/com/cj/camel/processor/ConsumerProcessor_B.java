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
public class ConsumerProcessor_B implements Processor {

    Logger logger = LoggerFactory.getLogger(ConsumerProcessor_B.class);

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
        logger.info("@@@ConsumerProcessor_B is running......");

        //Get parameters from uri.
        String conditionId = (String) Optional.ofNullable(exchange)
                .map(Exchange::getIn)
                .map(message -> message.getHeader("cId"))
                .orElseThrow(() -> new RuntimeException("Header cId is null!"));

        //Sent body back to customer as response.
        exchange.getOut().setBody("<html><body>ConsumerProcessor_B: the conditionId is: [" + conditionId + "] </body></html>");

        logger.info("@@@ConsumerProcessor_B finished......");
    }






    public static void main(String[] args) {
        HashMap<String, String> map = null;

        Optional.ofNullable(map)
                .map(o -> o.get("key1"))
                .orElseThrow(() -> new RuntimeException("Map is empty!")); //可以抛出自定义异常.

    }
}
