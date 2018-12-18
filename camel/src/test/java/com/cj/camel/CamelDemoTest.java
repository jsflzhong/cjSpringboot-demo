package com.cj.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

public class CamelDemoTest {

    @Test
    public void demo() throws Exception {
        //Add context
        DefaultCamelContext context = new DefaultCamelContext();

        //Add routers
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                System.out.println("@@@running...");
                from("timer://foo?fixedRate=true&delay=0&period=10000")
                        .to("http4:www.baidu.com?bridgeEndpoint=true")
                        .to("file:/home/cuijian/test/baidu?fileName=baidu.html");
            }
        });

        //start
        context.start();

        while (1==1) {
        }
    }

}
