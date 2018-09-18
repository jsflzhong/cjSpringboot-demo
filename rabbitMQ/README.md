0.How to ensure the high availability of the message in the RabbitMQ?

    1>.Message durable.
        See the Article 8 below.
        
    2>.Call back    
        See the Article 9 below.


1.pom

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
    

2.configuration

    1>.application.properties  
        
    2>.com.cj.rabbitMQ.configuration.ConsumerConfituration
    
    3>.com.cj.rabbitMQ.configuration.ProducerConfiguration


3.Message producer and consumer
    
    1>.producer:
        com.cj.rabbitMQ.producer.*
        
    2>consumer:
        com.cj.rabbitMQ.consumer.*
        
    Attention: Multi consumers should be in the multi classes.
    
    Attention: The message will be send into multi queues if and only if the they bind the same exchange and the routing key as in the convertAndSend(...) function.  
    
    Attention: Then message will NOT be seen in the queue page in the rabbitMQ admin if they were consumed by the consumer already.


4.Test

    RabbitMqMainTest  --Tested
    OR:
    com.cj.rabbitMQ.controller.RabbitMQController


5.Attention:
    
    在Spring Boot中实现了RabbitMQ的自动配置，在配置文件中添加如下配置信息
    spring.rabbitmq.host=localhost
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=test
    spring.rabbitmq.password=test
    spring.rabbitmq.virtualHost=test
    
    后会自动创建ConnectionFactory以及RabbitTemplate对应Bean，为什么上面我们还需要手动什么呢?
    
    自动创建的ConnectionFactory无法完成事件的回调，即没有设置下面的代码
    connectionFactory.setPublisherConfirms(true);


6.Exception1:
    
    Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED - inequivalent arg 'type' for exchange 'jsflzhong.exchange1' in vhost '/': received 'direct' but current is 'topic', class-id=40, method-id=10)
    原因:
    网上: 在配置中配置的队列,所以如果该队列已经在管理后台配置好了的话,就会报这个错. 应该从后台删掉那个队列即可,或配置这里用新的一套.
    这种错误基本都是这个原因.但是,如果仔细看,会发现是exchange策略的问题.后台配置的是topic,而配置里配置的是direct. 修改其一即可.而不用删除或新建一套队列.


7.Exception2:
    
    Caused by: org.springframework.amqp.AmqpException: No method found for class [B
    原因:
    需要在消费者配置中,配置一个json的转换器.
    见:com.cj.rabbitMQ.configuration.ConsumerConfituration.rabbitListenerContainerFactory
    然后在消费者类注解的元素中使用.
    见:com.cj.rabbitMQ.consumer.Receiver_String1


8.Durable:

    1>.Exchange durable: com.cj.rabbitMQ.configuration.ConsumerConfituration.getExchange
    
    2>.Queue durable: com.cj.rabbitMQ.configuration.ConsumerConfituration.getQueue
    
    3>.Message durable: Already set by default.
        Entrance of api: convertAndSend(...)
        class: .m2/repository/org/springframework/amqp/spring-amqp/2.0.5.RELEASE/spring-amqp-2.0.5.RELEASE.jar!/org/springframework/amqp/core/MessageProperties.class:533

    Tested:
        1>.Comment out all the consumers in the project.
        
        2>.Start test class, let producer produce a few message into the queue.
            The message will be stay in the queue 'cause there is no consumer any more.
        
        3>.Check out the message in the mq in the page of RabbitMQ admin.
        
        4>.Shut down the RabbitMQ service on the server.
        
        5>.Star up the RabbitMQ service on the server.
        
        6>.Check out the message in the mq in the page of RabbitMQ admin.
            The message previously is still there in the queue!
            TEST SUCCESS!


9.Call back(auto):

    1>.com.cj.rabbitMQ.configuration.ProducerConfiguration.connectionFactory
    
    2>.com.cj.rabbitMQ.producer.ProducerWithCallback.confirm


10.Dead message:

        


11.Done:

    0>.Use routingKey to bind the exchange and queue.

    1>.String message transfer.
    
    2>.Call back.
    
    3>.Multi consumer.
    
    4>.Duable of exchange,queue and message.
    
    