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
public class MyFoodProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(MyFoodProcessor.class);

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
        logger.info("@@@Processing......");

        //Get parameters from uri.
        String param1 = (String) Optional.ofNullable(exchange)
                .map(o -> o.getIn().getHeader("test1"))
                .orElse("empty param!");
        String param2 = (String) Optional.ofNullable(exchange)
                .map(o -> o.getIn().getHeader("test2"))
                .orElse("empty param!");

        logger.info("@@@The params from URI are: {},{} ", param1, param2);

        //Sent body back to customer as response.
        exchange.getOut().setBody("<html><body>Food is beautiful!</body></html>");
    }






    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1","");

        Optional.ofNullable(map)
                .map(o -> o.get("key1"))
                .orElseThrow(() -> new RuntimeException("Map is empty!")); //可以抛出自定义异常.



    }
}
