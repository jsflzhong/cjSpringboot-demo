package com.cj.camel.router;

import com.cj.camel.processor.*;
import org.apache.camel.model.language.JsonPathExpression;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 带逻辑判断的router.
 * 功能: 根据request中自定义的header属性携带的不同的值,动态的判断,从而路由到不同的processor中去.
 * 注意: camel自己封装了很多关于逻辑判断的方法,例如:choice(), when(),otherwise(), endChoice()
 *
 * @author cj
 */
@Component
public class ContentBasedRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(ContentBasedRouter.class);


    /**
     * Tested
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {

        logger.info("@@@ContentBasedRouter is running...");

        //这是一个内置的"JsonPath表达式",用于从http携带的json信息中,直接提取"conditionId"这个自定义的属性的值.
        JsonPathExpression jsonPathExpression = new JsonPathExpression("$.data.conditionId");
        jsonPathExpression.setResultType(String.class);

        from("jetty:http://0.0.0.0:8086/contentBasedRouter")
                // 将conditionId属性的值存储 exchange in Message的header中，以便后续进行判断.
                // 之所以再次封装,是因为上面从header拿到的属性返回值类型,不是String.
                .process(new ContentBasedProcessor())
                //.setHeader("cId", jsonPathExpression) //实测,这里不能这样设置header,否则服务自动关闭.可以在上面的processor中设置header,这样下面就可以读出来了.
                //.setHeader("cId", () -> "test2") //实测,这样是可以的,怀疑是上面的jsonPathExpression的值有问题.
                //开始判断逻辑
                .choice() //1.choice
                    .when(header("cId").isEqualTo("test1")) //2.when
                        .process(new ConsumerProcessor_A())
                    .when(header("cId").isEqualTo("test2"))
                        .process(new ConsumerProcessor_B())
                    .otherwise()    //3.otherwise
                        .process(new ConsumerProcessor_C())
                .endChoice(); //4.endChoice

        logger.info("@@@ContentBasedRouter is finishing...");
    }
}
