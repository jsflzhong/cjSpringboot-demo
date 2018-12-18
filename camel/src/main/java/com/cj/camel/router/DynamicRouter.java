package com.cj.camel.router;

import com.cj.camel.processor.ConsumerProcessor_A;
import org.apache.camel.Properties;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态循环路由.
 * <p>
 * 作用:动态循环路由的特点是开发人员可以通过条件表达式等方式，动态决定下一个路由位置
 * <p>
 * 原理:(非Exchange复制)在下一路由位置处理完成后Exchange将被重新返回到路由判断点，并由动态循环路由再次做出新路径的判断。
 * 如此循环执行直到动态循环路由不能再找到任何一条新的路由路径为止
 * <p>
 * 与之前的动态路由（recipientList,即类:RecipientRouter）的区别是:
 * 1.dynamicRouter一次选择只能确定一条路由路径，而recipientList只进行一次判断并确定多条路由分支路径；
 * 2.dynamicRouter确定的下一路由在执行完成后，Exchange对象还会被返回到dynamicRouter中以便开始第二次循环判断(即: exchange对象是不复制的)，
 * 而recipientList会为各个分支路由复制一个独立的Exchange对象，并且各个分支路由执行完成后Exchange对象也不会返回到recipientList
 */
@Component
public class DynamicRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(DynamicRouter.class);

    /**
     * 动态循环路由.
     * Tested
     * @throws Exception Exception
     */
    @Override
    public void configure() throws Exception {
        logger.info("@@@DynamicRouter is running...");

        from("jetty:http://0.0.0.0:8089/dynamicRouter")
                // 使用dynamicRouter，进行“动态路由”循环，
                // 直到指定的下一个元素为null为止
                .dynamicRouter().method(this, "doDirect")//调用本类中的方法
                .process(new ConsumerProcessor_A()); //依然可以路由到其他processor上.

        logger.info("@@@DynamicRouter is finishing...");
    }

    /**
     * 该方法用于根据“动态循环”的次数，确定下一个执行的Endpoint
     * <p>
     * 在实际使用中，开发人员还可以有很多方式向dynamicRouter“动态循环路由”返回指定的下一Endpoint。
     * 例如使用JsonPath指定JSON格式数据中的某个属性值，
     * 或者使用XPath指定XML数据中的某个属性值，
     * 又或者使用header方法指定Exchange中Header部分的某个属性。
     * 但是无论如何请开发人员确定一件事情：
     * 向dynamicRouter指定下一个Endpoint的方式中是会返回null进行循环终止的，否则整个dynamicRouter会无限的执行下去。
     *
     * @param properties 通过注解能够获得的Exchange中properties属性，可以进行操作，并反映在整个路由过程中
     *                   注意:该方法已过期,找一下替代方案.
     * @return String
     */
    public String doDirect(@Properties Map<String, Object> properties) {
        // 在Exchange的properties属性中，取出Dynamic Router的循环次数
        AtomicInteger time = (AtomicInteger) properties.get("time");
        if (time == null) {
            time = new AtomicInteger(0);
            properties.put("time", time);
        }

        logger.info("这是Dynamic Router循环第：【{}】次执行！执行线程：{}",
                time.incrementAndGet(), Thread.currentThread().getName());

        // 第一次选择DirectRouteB
        if (time.get() == 1) {
            return "direct:consumerA";
        }
        // 第二次选择DirectRouteC
        else if (time.get() == 2) {
            return "direct:consumerB";
        }
        // 第三次选择一个Log4j-Endpoint执行
        else if (time.get() == 3) {
            return "log:DynamicRouter?showExchangeId=true&showProperties=ture&showBody=false";
        }

        // 注意:其它情况返回null，终止 dynamicRouter的执行. 这一步必须做!
        return null;
    }

    /**
     * Test log:
     * 2018-12-18 23:02:56.567 INFO  --- [qtp1909889813-88] com.cj.camel.router.DynamicRouter - 这是Dynamic Router循环第：【1】次执行！执行线程：qtp1909889813-88
     * 2018-12-18 23:02:56.568 INFO  --- [qtp1909889813-88] ConsumerRouters_A - Exchange[Id: ID-cuijian-ThinkPad-T430-1545144760198-0-7, ExchangePattern: InOut, BodyType: org.apache.camel.converter.stream.InputStreamCache, Body: [Body is instance of org.apache.camel.StreamCache]]
     * 2018-12-18 23:02:56.568 INFO  --- [qtp1909889813-88] com.cj.camel.router.DynamicRouter - 这是Dynamic Router循环第：【2】次执行！执行线程：qtp1909889813-88
     * 2018-12-18 23:02:56.569 INFO  --- [qtp1909889813-88] ConsumerRouters_B - Exchange[Id: ID-cuijian-ThinkPad-T430-1545144760198-0-7, ExchangePattern: InOut, BodyType: org.apache.camel.converter.stream.InputStreamCache, Body: [Body is instance of org.apache.camel.StreamCache]]
     * 2018-12-18 23:02:56.570 INFO  --- [qtp1909889813-88] com.cj.camel.router.DynamicRouter - 这是Dynamic Router循环第：【3】次执行！执行线程：qtp1909889813-88
     * 2018-12-18 23:02:56.570 INFO  --- [qtp1909889813-88] DynamicRouter - Exchange[Id: ID-cuijian-ThinkPad-T430-1545144760198-0-7, ExchangePattern: InOut, BodyType: org.apache.camel.converter.stream.InputStreamCache]
     * 2018-12-18 23:02:56.570 INFO  --- [qtp1909889813-88] com.cj.camel.router.DynamicRouter - 这是Dynamic Router循环第：【4】次执行！执行线程：qtp1909889813-88
     * 2018-12-18 23:02:56.571 INFO  --- [qtp1909889813-88] c.c.c.processor.ConsumerProcessor_A - @@@ConsumerProcessor_A is running......
     * 2018-12-18 23:02:56.571 INFO  --- [qtp1909889813-88] c.c.c.processor.ConsumerProcessor_A - @@@ConsumerProcessor_A finished......
     */
}
