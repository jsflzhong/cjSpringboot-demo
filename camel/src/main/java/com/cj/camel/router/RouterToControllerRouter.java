package com.cj.camel.router;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

/**
 * 3.My test that use the router to route the specific request to specific controller.
 */
@Component
public class RouterToControllerRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(RouterToControllerRouter.class);

    @Autowired
    private CsdnRouter1 csdnRouter1;

    /**
     * 拿到请求,解析请求body,将其封装进Exchange,
     * 用该Exchange请求目标自定义的Controller并携带json格式的数据.
     * Controller那边用@RequestBody接受该json参数.
     * 测试:
     * POST
     * http://localhost:8084/toController
     * body:{"key1":"value1","key2":"value2"}
     * header: content-type:application/json
     * <p>
     * Tested
     *
     * @throws Exception Exception
     * @author cj
     */
    @Override
    public void configure() throws Exception {
        logger.info("@@@RouterToControllerRouter is running...");

        from("jetty:http://localhost:8084/toController").process(exchange -> {
            Object o = Optional.ofNullable(exchange)
                    .map(Exchange::getIn)
                    .map(Message::getBody)
                    .orElseThrow(() -> new RuntimeException("@@@Empty body!"));

            String requestBody;
            try (InputStream bodyIS = (InputStream) o) {
                requestBody = csdnRouter1.analysisMessage(bodyIS);
            }

            //把生产者携带的请求body数据,封装进exchange中,准备携带给消费者.
            exchange.getOut().setBody(requestBody);
            //注意,如果消费者那边的Controller是用@ResponseBody来接受json数据的话,这里必须手动设定本次请求的content-type.
            exchange.getOut().setHeader(HTTP.CONTENT_TYPE, "application/json");

        })
                //请求自定义的controller,即本次的消费者.
                .to("jetty:http://localhost:8081/RouterToController")
                //同时也把信息顺便打印到控制台.
                .to("log:RouterToControllerRouter?showExchangeId=true");

        logger.info("@@@RouterToControllerRouter finish running...");
    }
}
