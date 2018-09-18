package com.cj.eventBus.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jian.Cui on 2018/8/29.
 * 单例化线程池
 */
public class ExecutorsFactory {

    private ExecutorsFactory(){}

    private static ExecutorService executorService;

    static {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 20);
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
