package com.cj.eventBus.subscriber;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jian.Cui on 2018/8/29.
 * The second subscriber
 *
 * @author cj
 */
public class SecondSubscriber implements Subscriber {

    private static final Logger logger = LoggerFactory.getLogger(SecondSubscriber.class);

    @Subscribe
    public void subscribe(String stringEvent) {
        logger.info("@@@Second subscriber got the event:{}",stringEvent);
    }

}
