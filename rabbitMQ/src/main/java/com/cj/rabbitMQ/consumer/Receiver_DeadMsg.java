package com.cj.rabbitMQ.consumer;

import com.cj.rabbitMQ.configuration.ConsumerConfituration;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by jsflz on 2018/8/2.
 * Used to consume the msg from dead queue.
 *
 * @author cj
 */
@Component
//This is the name of queue, which is listening by this consumer!
public class Receiver_DeadMsg {

    Logger logger = LoggerFactory.getLogger(Receiver_DeadMsg.class);

    /**
     * Consume the msg from dead queue.
     * Tested
     *
     * @param message Message
     */
    @RabbitListener(queues = ConsumerConfituration.QUEUE_NAME_DEAD, containerFactory = "rabbitListenerContainerFactory")
    public void process1(Message message, Channel channel) {
        //Attention: Must use:"new String (message.getBody())" instead of "message.getBody()",or the msg will be a binary array!
        logger.info("@@@Consumer_Dead: Got the msg from the dead queue!!! The msg is:{} ", new String(message.getBody()));
    }
}
