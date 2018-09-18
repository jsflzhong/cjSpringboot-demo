package com.cj.eventBus;

import com.cj.eventBus.factory.AsyncEventBusFactory;
import com.cj.eventBus.subscriber.MonitorCounterSubscriber;
import com.cj.eventBus.subscriber.SecondSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jian.Cui on 2018/8/29.
 * Test class
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        //注册第一个订阅者,然后发送事件.
        AsyncEventBusFactory.postEvent(new MonitorCounterSubscriber(), "stringEvent1 !!~~");

        logger.info("@@@The main current thread is keep running asynchronously after send the event!");

        //注册第二个订阅者,然后发送事件.(注意观察第一个订阅者是否还能收到该事件,目标是不应该收到,因为这次只注册了第二个订阅者.
        //但是由于EventBus做成单例了,而且在上面又注册过第一个订阅者,所以第一个订阅者还是可能收到的)
        //经过测试,如果不做处理,那么第一个果然还是能收到.
        //所以在eventBus工厂那边的postEvent方法里,在post事件之后,把已注册的订阅者给取消注册了.
        //现在只有每次传入的订阅者才可以收到事件了.
        AsyncEventBusFactory.postEvent(new SecondSubscriber(), "stringEvent2 !!~~");
    }

    /**
     * Test log:

     2018-09-04 15:11:52.306 INFO  --- [main] com.cj.eventBus.Main - @@@The main current thread is keep running asynchronously after send the event!
     2018-09-04 15:11:52.306 INFO  --- [pool-1-thread-1] c.c.e.s.MonitorCounterSubscriber - @@@First subscriber got the event:stringEvent1 !!~~
     2018-09-04 15:11:52.312 INFO  --- [pool-1-thread-2] c.c.e.subscriber.SecondSubscriber - @@@Second subscriber got the event:stringEvent2 !!~~
     */
}

