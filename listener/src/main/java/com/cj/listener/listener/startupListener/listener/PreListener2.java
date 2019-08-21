package com.cj.listener.listener.startupListener.listener;


import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 在容器启动完成后调用.
 *
 * 注意:
 *  如果需要有顺序执行，我们可以实现Ordered接口，值越小，越先执行。
 */
@Component
@Slf4j
public class PreListener2 implements ApplicationListener<ApplicationStartedEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("@@@PreListener2 onApplicationEvent...");
    }

    @Override
    public int getOrder() {
        log.info("@@@PreListener2 getOrder...");
        return 0;
    }
}
