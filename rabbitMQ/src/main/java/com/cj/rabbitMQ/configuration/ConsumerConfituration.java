package com.cj.rabbitMQ.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.cj.rabbitMQ.configuration.ConsumerConfituration.QUEUE_NAME;

/**
 * Created by Jian.Cui on 2018/9/6.
 * Configuration of consumer
 */
@Configuration
@RabbitListener(queues = QUEUE_NAME)
public class ConsumerConfituration {

    Logger logger = LoggerFactory.getLogger(ConsumerConfituration.class);

    // 交换空间exchange名称
    public static final String EXCHANGE = "jsflzhong.exchange1";
    // 设置路由keyl
    public static final String ROUTINGKEY = "jsflzhong.rk1";
    // 队列名称.如果mq服务上没有这个队列,则这里的设置将会为mq服务自动新建一个队列.
    public static final String QUEUE_NAME = "jsflzhong.queue1";

    // 交换空间exchange2名称
    public static final String EXCHANGE2 = "jsflzhong.exchange2";
    // 设置路由key2
    public static final String ROUTINGKEY2 = "jsflzhong.rk2";
    // 队列2
    public static final String QUEUE_NAME2 = "jsflzhong.queue2";

    // The name of Dead Exchange
    public static final String EXCHANGE_DEAD = "jsflzhong.dead.exchange";
    // The name of routing key of the dead queue
    public static final String ROUTINGKEY_DEAD = "jsflzhong.dead.rk1";
    // The name of Dead Queue
    public static final String QUEUE_NAME_DEAD = "jsflzhong.dead.queue1";

    @Value("${spring.rabbitmq.addresses}")
    private String addresses;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    /**
     * Configure the type of exchange.
     * Tested
     *
     * @author cj
     */
    @Bean
    public TopicExchange getExchange() {
        /**
         * DirectExchange:按routingkey直接匹配
         * TopicExchange: 按routingkey使用通配符匹配
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
         * HeadersExchange ：通过添加属性key-value匹配
         */
        //return new TopicExchange(EXCHANGE);

        //Open exchange durable.
        return new TopicExchange(EXCHANGE, true, false);
    }

    /**
     * Configure the queue
     * Business Queue
     * Tested
     *
     * @return Queue
     * @author cj
     */
    @Bean
    public Queue getQueue() {
        //return new Queue(QUEUE_NAME);

        //Open queue durable.
        return new Queue(QUEUE_NAME, true, false, false);
    }

    /**
     * Use routingKey to bind the exchange and queue.
     * Tested
     *
     * @return Binding
     * @author cj
     */
    @Bean
    public Binding binding() {
        //将队列绑定到交换机
        return BindingBuilder.bind(getQueue()).to(getExchange()).with(ROUTINGKEY);
    }

    /**
     * Configure the json converter.
     * Can be used as the element in the annotation:@RabbitListener in the consumer classes.
     * Tested
     *
     * @param connectionFactory connectionFactory
     * @return SimpleRabbitListenerContainerFactory
     * @author cj
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /*
    //The consumer can be also here.
    @RabbitHandler
    public void process(@Payload String foo) {
        logger.info("@@@Receiver: " + foo);
    }*/



    /**########################################## DLX #########################################*/

    /**
     * Configure the type of exchange2
     * Tested
     *
     * @author cj
     */
    @Bean
    public TopicExchange getExchange2() {
        /**
         * DirectExchange:按routingkey直接匹配
         * TopicExchange: 按routingkey使用通配符匹配
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
         * HeadersExchange ：通过添加属性key-value匹配
         */
        //Open exchange durable.
        return new TopicExchange(EXCHANGE2, true, false);
    }

    /**
     * Configure the queue2
     * Business Queue (Dead queue configured!)
     * Attention:
     * When we wanna use queue B as queue A's dead queue,then we have to configure extra information in the queue A's configuration here.
     * The extra information include: The exchange and routing-key of the dead queue!
     * So the queue A knows where to send the msg if it is a dead one!
     * Tested
     *
     * @return Queue
     * @author cj
     */
    @Bean
    public Queue getQueue2() {

        //Need to configure a map to hold the extra information about the DLX.
        Map<String,Object> args=new HashMap<>();
        // 设置该Queue的死信的信箱
        args.put("x-dead-letter-exchange", EXCHANGE_DEAD);
        // 设置死信routingKey
        args.put("x-dead-letter-routing-key", ROUTINGKEY_DEAD);

        //Add one param more than before!
        return new Queue(QUEUE_NAME2, true, false, false, args);
    }

    /**
     * Use routingKey to bind the exchange2 and queue.
     * Tested
     *
     * @return Binding
     * @author cj
     */
    @Bean
    public Binding binding2() {
        //将队列绑定到交换机
        return BindingBuilder.bind(getQueue2()).to(getExchange2()).with(ROUTINGKEY2);
    }

    /**
     * Configure the DLX exchange.
     * Tested
     *
     * @author cj
     */
    @Bean
    public TopicExchange getDeadExchange() {
        /**
         * DirectExchange:按routingkey直接匹配
         * TopicExchange: 按routingkey使用通配符匹配
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
         * HeadersExchange ：通过添加属性key-value匹配
         */
        //Open exchange durable.
        return new TopicExchange(EXCHANGE_DEAD, true, false);
    }

    /**
     * Configure the dead queue.
     * Tested
     *
     * @return Queue
     * @author cj
     */
    @Bean
    public Queue getDeadQueue() {
        return new Queue(QUEUE_NAME_DEAD, true);
    }

    /**
     * Use routingKey to bind the dead exchange and queue.
     * Tested
     *
     * @return Binding
     * @author cj
     */
    @Bean
    public Binding bindingDead() {
        //将队列绑定到交换机
        return BindingBuilder.bind(getDeadQueue()).to(getDeadExchange()).with(ROUTINGKEY_DEAD);
    }
}
