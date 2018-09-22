import com.alibaba.fastjson.JSONObject;
import com.cj.rabbitMQ.Application;
import com.cj.rabbitMQ.consumer.Receiver_String1;
import com.cj.rabbitMQ.domain.UserT;
import com.cj.rabbitMQ.producer.ProducerDead;
import com.cj.rabbitMQ.producer.ProducerWithCallback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by jsflz on 2018/8/2.
 * Test class for RabbitMq
 *
 * @author cj
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitMqMainTest {

    @Autowired
    private ProducerWithCallback testSender; //rabbitmq的测试用的消息生产者.
    @Autowired
    private Receiver_String1 testReceiver; //rabbitmq的测试用的消息消费者.
    @Autowired
    private ProducerDead producerDead; //Producer for dead msg.

    /**
     * Test to send string msg.
     * Tested
     *
     * @author cj
     */
    @Test
    public void RabbitMqSender_string() {
        for (int i = 0; i < 5; i++) {
            testSender.send("msg:" + (i + 1));
        }
    }

    /**
     * Test to send Message.
     *
     * @author cj
     * @deprecated Not working yet!
     * Untested
     */
    @Test
    @Deprecated
    public void RabbitMqSender_Message() {
        String msg = "messageMessage";
        Message message = MessageBuilder
                .withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        testSender.sendMessage(message);
    }

    /**
     * Test to send pojo msg.
     */
    @Test
    public void RabbitMqSender_pojo() {
        for (int i = 0; i < 10; i++) {
            UserT userT = new UserT()
                    .setId(i)
                    .setAge(i + 1)
                    .setPassword("111")
                    .setUserName("222");
            testSender.send("******" + userT);
        }

    }

    /**
     * Test to send Dead string msg (Rejected by the consumer).
     * DLX
     * Tested
     *
     * @author cj
     */
    @Test
    public void RabbitMqSender_Dead() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1","value2");
        producerDead.send(jsonObject.toJSONString());
    }

}
