package com.cj.rabbitMQ.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by jsflz on 2018/8/2.
 * RabbitMq receiver1
 * Attention: Multi consumers should be in the multi classes.
 * 消费者负责申明交换机(生产者也可以申明)、队列、两者的绑定操作。
 *
 * @author cj
 */
@Component
//This is the name of queue, which is listening by this consumer!
@RabbitListener(queues = "jsflzhong.queue1", containerFactory="rabbitListenerContainerFactory")
public class Receiver_String1 {

    Logger logger = LoggerFactory.getLogger(Receiver_String1.class);

    /**
     * Tested
     * @param hello msg
     */
    @RabbitHandler
    public void process1(String hello) {
        logger.info("@@@@@@Receiver_String1: the msg is:{} ",hello );
    }
}
