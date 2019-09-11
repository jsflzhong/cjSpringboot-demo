package com.cj.listener.listener.startupListener.listener;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * springboot 加载完成时候执行的事件
 *
 * 注意:
 *  如果需要有顺序执行，我们可以实现Ordered接口，值越小，越先执行。
 */
@Component
@Slf4j
public class PreListener3 implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    @Override
    public int getOrder() {
        log.info("@@@PreListener3 getOrder...");
        return 0;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("@@@PreListener3 onApplicationEvent...");
    }
}
