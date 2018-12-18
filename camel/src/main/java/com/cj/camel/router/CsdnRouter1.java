package com.cj.camel.router;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * 2.A test from CSDN.
 */
@Component
public class CsdnRouter1 extends SpringRouteBuilder {

    /**
     * 测试方法:POST请求:http://localhost:8283/doHelloWorld
     * body中携带一些数据即可.
     * Tested
     *
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {
        //从任意IP,和8283端口,和doHelloWorld路径的 访问的请求都会被此处拦截
        from("jetty:http://0.0.0.0:8283/doHelloWorld")

                //自定义processor
                .process(exchange -> {
                    // 因为很明确消息格式是http的，所以才使用这个类
                    // 否则还是建议使用org.apache.camel.Message这个抽象接口
                    /*HttpMessage message = (HttpMessage)exchange.getIn();
                    InputStream bodyStream =  (InputStream)message.getBody();*/

                    //注释掉上面的两行,改为用Optional.
                    Object o1 = Optional.ofNullable(exchange)
                            .map(Exchange::getIn)
                            .map(Message::getBody)
                            .orElseThrow(() -> new RuntimeException("@@@Empty body!"));

                    //把请求的body中的数据,转换为String.
                    String inputContext;
                    try (InputStream bodyStream = (InputStream) o1) {
                        inputContext = analysisMessage(bodyStream);
                    }

                    // 存入到exchange的out区域
                    if (exchange.getPattern() == ExchangePattern.InOut) {
                        exchange.getOut().setBody(inputContext + " || out");
                    }
                })

                //打印到console.
                .to("log:CsdnRouter1?showExchangeId=true");
                //打印的结果:2018-12-14 11:07:22.290 INFO  --- [qtp422969011-55] CsdnRouter1 -
        // Exchange[Id: ID-cuijian-ThinkPad-T430-1544754894984-0-1, ExchangePattern: InOut, BodyType: String, Body: {"key1":"value1"} || out]
    }

    /**
     * 从stream中分析字符串内容
     *
     * @param bodyStream bodyStream
     * @return String
     */
    public String analysisMessage(InputStream bodyStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] contextBytes = new byte[4096];
        int realLen;
        while ((realLen = bodyStream.read(contextBytes, 0, 4096)) != -1) {
            outStream.write(contextBytes, 0, realLen);
        }

        // 返回从Stream中读取的字串
        try {
            return new String(outStream.toByteArray(), "UTF-8");
        } finally {
            outStream.close();
        }
    }
}