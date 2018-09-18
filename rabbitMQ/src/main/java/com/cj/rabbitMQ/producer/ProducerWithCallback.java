package com.cj.rabbitMQ.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by jsflz on 2018/8/2.
 * RabbitMQ producer with exchange and routing key.
 *
 * @author cj
 */
@Component
public class ProducerWithCallback implements RabbitTemplate.ConfirmCallback,
        RabbitTemplate.ReturnCallback, InitializingBean {

    Logger logger = LoggerFactory.getLogger(ProducerWithCallback.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // The name of exchange
    private static final String EXCHANGE = "jsflzhong.exchange1";
    // The name of routing key
    private static final String ROUTINGKEY = "jsflzhong.rk1";

    /**
     * 1.Send string msg with exchange and routing_key
     * Tested.
     *
     * @param msg string msg.
     * @author cj
     */
    public void send(String msg) {
        //生成mq的消息id.
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        logger.info("@@@@@@sender: the msg is :{}", msg);

        //Exchange,routingKey,msg,and msgId.
        //Attention: When sending,the sender only consider the exchange and routingKey,they will decide where the message is going.
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTINGKEY, msg, correlationId);
    }

    /**
     * 2.实现ConfirmCallback
     * ACK=true仅仅标示消息已被Broker接收到，并不表示已成功投放至消息队列中
     * ACK=false标示消息由于Broker处理错误，消息并未处理成功
     * 需要配置下面的afterPropertiesSet()之后,这里才能生效.
     * Tested.
     *
     * @param correlationData correlationData
     * @param ack             ack See the explain previously
     * @param cause
     * @author cj
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("@@@confirm...The id is : {}", correlationData.getId());
        if (ack) {
            logger.info("@@@confirm...Msg consume success! cause:{}", cause);
        } else {
            logger.info("@@@confirm...Msg consume failed!!! cause:{}", cause);
        }
    }

    /**
     * 3.实现ReturnCallback
     * 当消息发送出去找不到对应路由队列时，将会把消息退回
     * 如果有任何一个路由队列接收投递消息成功，则不会退回消息
     * Untested
     *
     * @param message    message
     * @param replyCode  replyCode
     * @param replyText  replyText
     * @param exchange   exchange
     * @param routingKey routingKey
     * @author cj
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
                                String exchange, String routingKey) {
        logger.info("@@@消息发送失败: {}", Arrays.toString(message.getBody()));
    }

    /**
     * 4.实现InitializingBean
     * 设置消息送达、确认的方式
     * 这个非常重要. 之前没配这个,导致上面的callback无论如何都失效.
     * Tested
     *
     * @throws Exception Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
    }

    /**
     * 3.Send pojo msg.
     * @deprecated This function has no appropriate consumer!
     * Untested !
     *
     * @param msg msg
     * @author cj
     */
    @Deprecated
    public void sendMessage(Message msg) {
        //生成mq的消息id.
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        logger.info("@@@@@@sender: the msg is :{}", msg);

        //Exchange,routingKey,msg,and msgId.
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTINGKEY, msg, correlationId);
    }


}
