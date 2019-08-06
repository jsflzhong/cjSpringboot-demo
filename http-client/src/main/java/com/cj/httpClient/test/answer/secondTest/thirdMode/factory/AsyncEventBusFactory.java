package com.cj.httpClient.test.answer.secondTest.thirdMode.factory;

import com.cj.httpClient.test.answer.secondTest.thirdMode.observer.Observer;
import com.cj.httpClient.test.answer.secondTest.thirdMode.subject.Subject;
import com.google.common.eventbus.AsyncEventBus;

/**
 * A factory for asyncEventBus.
 *
 * @author Michael
 */
public class AsyncEventBusFactory {

    private AsyncEventBusFactory() {
    }

    private static AsyncEventBus alermEventBus =
            new AsyncEventBus("ASYNC_EVENT_BUS_FACTORY",
                    ExecutorsFactory.getExecutorService());

    /**
     * Register subscriber and publish the event.
     *
     * @param businessSubscribe subscriber
     * @param event             business parameter
     * @author Michael
     */
    public static void postEvent(Observer businessSubscribe, Subject event) {
        alermEventBus.register(businessSubscribe);
        alermEventBus.post(event);
        alermEventBus.unregister(businessSubscribe);
    }

}
