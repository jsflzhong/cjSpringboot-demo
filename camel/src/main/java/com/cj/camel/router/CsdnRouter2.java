package com.cj.camel.router;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Optional;

/**
 * 与CsdnRouter1差不多,这里用另一种方式解析request中的body中的内容.
 *
 */
@Component
public class CsdnRouter2 extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(CsdnRouter2.class);

    /**
     * 测试方法:POST请求:http://localhost:8010/CsdnRouter2
     * body中携带一些数据即可.
     * Tested
     *
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {
        logger.info("@@@CsdnRouter2 is running...");

        //从任意IP,和8283端口,和doHelloWorld路径的 访问的请求都会被此处拦截
        from("jetty:http://0.0.0.0:8010/CsdnRouter2")

                //自定义processor AI.
                .process(exchange -> {
                    try (
                            //读取request的body中的数据输入流.
                            InputStream body = Optional.ofNullable(exchange)
                                    .map(Exchange::getIn)
                                    //getBody方法可以指定类型,避免强转
                                    .map(message -> message.getBody(InputStream.class))
                                    .orElseThrow(() -> new RuntimeException("@@@Empty body!"))) {

                        //把请求的body中的数据,转换为String.
                        String bodyString = getBodyString(body);

                        exchange.getOut().setHeader(Exchange.FILE_NAME, "test1.txt");
                        exchange.getOut().setBody(bodyString);
                    }
                })
                //打印到console.
                .to("log:CsdnRouter2?showExchangeId=true");

        logger.info("@@@CsdnRouter2 is finishing...");

    }

    /**
     * 把request中的body中的信息的输入流,转换成字符串
     * @param body body
     * @return bodyString
     * @throws IOException IOException
     */
    private String getBodyString(InputStream body) throws IOException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(body))) {

            StringBuilder stringBuilder = new StringBuilder();
            String bodyString;
            while ((bodyString = bufferedReader.readLine()) != null) {
                stringBuilder.append(bodyString + "");
            }
            return stringBuilder.toString();
        }
    }
}