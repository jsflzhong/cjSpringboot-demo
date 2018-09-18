package com.cj.rabbitMQ.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new TopicExchange(EXCHANGE,true,false);
    }

    /**
     * Configure the queue
     * Tested
     *
     * @return Queue
     * @author cj
     */
    @Bean
    public Queue getQueue() {
        //return new Queue(QUEUE_NAME);

        //Open queue durable.
        return new Queue(QUEUE_NAME,true,false,false);
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


}
