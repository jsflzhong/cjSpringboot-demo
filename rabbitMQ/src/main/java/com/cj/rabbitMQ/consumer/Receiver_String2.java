package com.cj.rabbitMQ.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by jsflz on 2018/8/2.
 * RabbitMq receiver2
 * Attention: Multi consumers should be in the multi classes.
 *
 * @author cj
 */
@Component
//This is the name of queue, which is listening by this consumer!
@RabbitListener(queues = "jsflzhong.queue", containerFactory="rabbitListenerContainerFactory")
public class Receiver_String2 {

    Logger logger = LoggerFactory.getLogger(Receiver_String2.class);

    /**
     * Tested
     * @param hello msg
     */
    @RabbitHandler
    public void process1(String hello) {
        logger.info("@@@@@@Receiver_String2: the msg is:{} ",hello );
    }
}
