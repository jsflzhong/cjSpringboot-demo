package com.cj.rabbitMQ.controller;

import com.cj.rabbitMQ.producer.ProducerDead;
import com.cj.rabbitMQ.producer.ProducerWithCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Jian.Cui on 2018/9/6.
 */
@Controller
public class RabbitMQController {

    @Autowired
    private ProducerWithCallback testSender; //rabbitmq的测试用的消息生产者.
    @Autowired
    private ProducerDead producerDead; //Producer for dead msg.

    @ResponseBody
    @RequestMapping("/send")
    public Object send() {
        for (int i=0;i<10;i++){
            testSender.send("msg" + (i+1));
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping("/sendDLX")
    public Object sendDLX(String msg) {
        producerDead.send(msg);
        return "1";
    }
}
