package com.cj.rabbitMQ.configuration;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Configuration of producer
 *
 * @author cj
 */
@Configuration
public class ProducerConfiguration {

    // 交换空间exchange名称
    public static final String EXCHANGE = "jsflzhong.exchange1";
    // 设置路由keyl
    public static final String ROUTINGKEY = "jsflzhong.rk1";
    // 队列名称
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
     * 1.ConnectionFactory
     * Tested
     *
     * @return ConnectionFactory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        /** 如果要进行消息回调，则这里必须要设置为true */
        connectionFactory.setPublisherConfirms(publisherConfirms);
        return connectionFactory;
    }

    /**
     * 2.RabbitTemplate
     * Tested
     *
     * Producer可以调用这个方法:
     * public void convertAndSend(String exchange, String routingKey, final Object object, CorrelationData correlationData)
     * exchange:交换机名称
     * routingKey:路由关键字
     * object:发送的消息内容
     * correlationData:消息ID
     * <p>
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     * 如果需要在生产者需要消息发送后的回调，需要对rabbitTemplate设置ConfirmCallback对象，
     * 由于不同的生产者需要对应不同的ConfirmCallback，如果rabbitTemplate设置为单例bean，则所有的rabbitTemplate
     * 实际的ConfirmCallback为最后一次申明的ConfirmCallback。
     *
     * @return RabbitTemplate
     * @author cj
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)//必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }



    /*************************** 消费者配置 ***************************************/
    /*************************** 消费者配置已经单独拿到com.cj.rabbitMQ.configuration.ProducerConfiguration类中 ***************************************/
    /*************************** 这里的配置暂留,最后一个方法与那边不同,这是第二种方式. ***************************************/


    /**
     * 3.Exchange
     *
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列 (本例)
     * TopicExchange:多关键字匹配
     *//*
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE);
    }

    *//**
     * 4.Queues
     *
     * 使用在rabbitmq后台管理页面上配置好的queue的名字.
     *
     * @return Queue
     * @author cj
     *//*
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME,true);//队列持久
    }

    *//**
     * 5.Binding
     *
     * @return Binding
     *//*
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(ROUTINGKEY);
    }

    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {

            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("@@@receive msg : " + new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
            }
        });
        return container;
    }*/

}
