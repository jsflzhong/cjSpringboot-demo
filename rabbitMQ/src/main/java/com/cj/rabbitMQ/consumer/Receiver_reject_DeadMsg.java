package com.cj.rabbitMQ.consumer;

import com.cj.rabbitMQ.configuration.ConsumerConfituration;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by jsflz on 2018/8/2.
 * Used to reject the msg from business queue to make the msg to be a dead msg.
 *
 * @author cj
 */
@Component
//This is the name of queue, which is listening by this consumer!
public class Receiver_reject_DeadMsg {

    Logger logger = LoggerFactory.getLogger(Receiver_reject_DeadMsg.class);

    /**
     * Reject the msg from business queue to make the msg to be a dead msg.
     * Tested
     *
     * @param message Message
     */
    @RabbitListener(queues = ConsumerConfituration.QUEUE_NAME2, containerFactory = "rabbitListenerContainerFactory")
    public void process1(Message message, Channel channel) {
        //Attention: Must use:"new String (message.getBody())" instead of "message.getBody()",or the msg will be a binary array!
        logger.info("@@@Consumer_business: ready to reject the msg...then it will be pushed into the dead queue automatically! msg:{}", new String(message.getBody()));
        try {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);//Reject the msg.
        } catch (IOException e) {
            ExceptionUtils.getStackTrace(e);
        }

        //Never run!
        logger.debug("@@@Consumer_business: Dead message will be consumed after 5 sec,msg: {}", message.getBody());
    }
}
