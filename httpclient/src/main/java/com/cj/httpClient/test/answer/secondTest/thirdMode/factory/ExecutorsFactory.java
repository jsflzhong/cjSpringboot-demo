package com.cj.httpClient.test.answer.secondTest.thirdMode.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A factory for threadPool.
 *
 * @author Michael
 */
public class ExecutorsFactory {

    private ExecutorsFactory() {
    }

    private static ExecutorService executorService;

    static {
        //In real developing, could use: N threads = N CPU * U CPU * (1 + W/C)
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 20);
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
