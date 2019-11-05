package com.cj.concurrent.product.executor;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.ThreadContext;

public class CustomizedThreadPoolExecutor extends ThreadPoolExecutor {

    public CustomizedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                        TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(final Runnable command) {
        final Map<String, String> contextMap = ThreadContext.getImmutableContext();
        if (contextMap != null) {
            super.execute(wrap(command, contextMap));
        } else {
            super.execute(command);
        }
    }

    private Runnable wrap(final Runnable runnable, final Map<String, String> contextMap) {
        return () -> {
            ThreadContext.putAll(contextMap);
            try {
                runnable.run();
            } finally {
                ThreadContext.clearAll();
            }
        };
    }
}
