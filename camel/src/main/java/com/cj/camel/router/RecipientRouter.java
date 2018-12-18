package com.cj.camel.router;

import com.cj.camel.processor.ConsumerProcessor_A;
import org.apache.camel.ExchangePattern;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 静态路由
 * recipientRouter
 * 处理Dynamic Recipient List
 * 作用: 动态的把request路由到其他的router上, 路由到哪里,取决于request中的json中约定的信息.
 * 测试:
 * 发起POST请求,body为:{"data":{"routeName":"direct:consumerA,direct:consumerB"},"token":"d9c33c8f-ae59-4edf-b37f-290ff208de2e","desc":""}
 * 下方的代码中,会用内置的jsonpath(注意加pom依赖!!!)方法,直接从上述request的body中取出data下的routeName的两个值,从而路由到ConsumerRouters_A和ConsumerRouters_B中去.
 */
@Component
public class RecipientRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(RecipientRouter.class);

    /**
     * Tested
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {
        logger.info("@@@RecipientRouter is running...");

        from("jetty:http://0.0.0.0:8088/recipientRouter")
                .setExchangePattern(ExchangePattern.InOnly)
                .recipientList()
                .jsonpath("$.data.routeName")
                .delimiter(",")
                .end()
                .process(new ConsumerProcessor_A());//上面动态路由到其他router后,这里还可以继续添加需要执行的processor.

        logger.info("@@@RecipientRouter is finishing...");
    }
}
