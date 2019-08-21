package com.cj.listener.listener.startupListener.listener;


import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 在容器启动开始时调用.
 */
@Component
@Slf4j
public class PreListener1 implements ApplicationListener<ApplicationStartingEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        log.info("@@@PreListener1 onApplicationEvent...");
    }

    @Override
    public int getOrder() {
        log.info("@@@PreListener1 getOrder...");
        return 1;
    }
}
