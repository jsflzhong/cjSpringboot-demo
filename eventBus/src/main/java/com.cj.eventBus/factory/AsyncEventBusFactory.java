package com.cj.eventBus.factory;

import com.cj.eventBus.subscriber.Subscriber;
import com.google.common.eventbus.AsyncEventBus;

/**
 * Created by Jian.Cui on 2018/8/29.
 * 单例化异步EventBuse
 */
public class AsyncEventBusFactory {

    private AsyncEventBusFactory() {
    }

    private static AsyncEventBus alermEventBus =
            new AsyncEventBus("ASYNC_EVENT_BUS_FACTORY", ExecutorsFactory.getExecutorService());

    /**
     * 注册事件订阅者,并发布事件
     *
     * @param businessSubscribe 事件订阅者
     * @param stringEvent       订阅者那边需要的业务参数. 根据业务定义即可.
     * @author cj
     */
    public static void postEvent(Subscriber businessSubscribe, String stringEvent) {
        alermEventBus.register(businessSubscribe);
        alermEventBus.post(stringEvent);
        //为了让本EventBus工厂能复用给不同的事件和订阅者,这里每次在post事件之后,把工厂中已注册的订阅者去除掉.
        alermEventBus.unregister(businessSubscribe);
    }
}
