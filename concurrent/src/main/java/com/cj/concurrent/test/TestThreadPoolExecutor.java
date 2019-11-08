package com.cj.concurrent.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {

    public static void main(String[] args) {
        ThreadPoolExecutor tp = init();
        tp.prestartAllCoreThreads();
        for (int i = 0; i <= 10; i++) {
            tp.execute(new MyTask("task_" + i));
        }
    }

    /**
     * 限制了任务队列的容量,用于测试任务积压的数量超过这个数后,是否会触发RejectedExecutionHandler;
     * 结论: maximumPoolSize + 任务队列的容量(本例中=3+2=5), 这个数值就是该线程池能同时接受的最大任务数,超过后就会触发RejectedExecutionHandler .
     *
     * 测试log:
     * @@@Current thread:MyTask [name=task_1] is sleeping...
     * @@@errorMyTask [name=task_5] rejected  //从零开始计算.
     * @@@Current thread:MyTask [name=task_0] is sleeping...
     * @@@errorMyTask [name=task_6] rejected
     * @@@errorMyTask [name=task_7] rejected
     * @@@Current thread:MyTask [name=task_2] is sleeping...
     * @@@errorMyTask [name=task_8] rejected
     * @@@errorMyTask [name=task_9] rejected
     * @@@errorMyTask [name=task_10] rejected
     * @@@Current thread:MyTask [name=task_3] is sleeping...
     * @@@Current thread:MyTask [name=task_4] is sleeping...
     */
    public static ThreadPoolExecutor init() {
        return new ThreadPoolExecutor(2, 3, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(2),
                (r, executor) -> System.out.println("@@@error" + r.toString() + " rejected")
        );
    }

    static class MyTask implements Runnable {
        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println("@@@Current thread:" + this.toString() + " is sleeping...");
                //System.out.println(this.toString() + " is running!");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }
}
