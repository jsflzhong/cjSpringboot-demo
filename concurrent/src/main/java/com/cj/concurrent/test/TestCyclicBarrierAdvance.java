package com.cj.concurrent.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by jsflz on 2018/8/10.
 * 测试:栅栏 CountDownLatch
 * 作用:让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
 *      可以把这个屏障点想象成:每个子线程处理完业务后,而不是处理业务前. 这样就达到了闭锁或CompletionService的效果! 并且不用循环主线程来聚拢数据!
 * API:
 *  await() - 阻塞当前线程,直到所有线程(构造器指定数量)都达到屏障点后,所有被该点阻塞的线程会一起醒来.
 *
 * 本例的目的:
 *  要证实: 栅栏 CyclicBarrier 可以作为闭锁 CountDownLatch 和 CompleteService的进阶使用!
 *  由于闭锁要维护一个计数器,而CompleteService又要循环主线程来聚拢数据,所以,这里要用栅栏来避免这两种不便利的情况!!
 *  测试:
 *      用栅栏 CyclicBarrier 做到: 在 CompleteService 中,主线程等待两条子线程完成业务返回数据后,循环主线程,聚拢数据 的工作.
 */
public class TestCyclicBarrierAdvance {

    /**
     * 测试:用栅栏来做到
     *
     * @throws InterruptedException InterruptedException
     * @author cj
     */
    private void test1() throws InterruptedException {

        //由主线程新建一个栏内线程数为3的栅栏.
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        //生成一个用于聚拢所有子线程执行后返回的数据的容器.
        List<testPojo> testPojoList = new ArrayList<>();

        //实际业务中,子线程的创建和执行,可以在for循环中进行.这里为了清晰所以分别创建.

        //由主线程新建子线程1.
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() +"线程开始处理业务.");

                //可以在这里处理业务代码...
                testPojo testPojo = new testPojo("1");

                System.out.println(Thread.currentThread().getName() +"线程完成业务处理,将要把处理结果放入容器.");
                testPojoList.add(testPojo);
                System.out.println(Thread.currentThread().getName() + "线程处理业务和结果完毕,将进入屏障点.");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1");

        //由主线程新建子线程2.
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() +"线程开始处理业务.");

                //可以在这里处理业务代码...
                testPojo testPojo = new testPojo("2");

                System.out.println(Thread.currentThread().getName() +"线程完成业务处理,将要把处理结果放入容器.");
                testPojoList.add(testPojo);
                System.out.println(Thread.currentThread().getName() + "线程处理业务和结果完毕,将进入屏障点.");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2");

        //由主线程开启两条子线程.
        thread1.start();
        thread2.start();

        try {
            System.out.println(Thread.currentThread().getName() + "主线程开启了两条子线程后,自己即将进入屏障点!@@@");
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + "主线通过了屏障点! 说明所有指定的线程都到达的屏障点,所以栅栏打开了,所有线程都应该通过了屏障点!即: 其他子线程完成了各自的业务@@@");
            System.out.println(Thread.currentThread().getName() + "开始获取其他线程处理完成的数据,数据已由各子线程完成聚拢,数据容器的大小为:" + testPojoList.size() + ".@@@");
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试用pojo,
     * 为上面子线程任务一个实体
     * 测试可以在子任务结束后,可以返回所处理的实体的id或其他信息.
     *
     * @author cj
     */
    public class testPojo {
        private String id;

        public String getId() {
            return id;
        }

        public testPojo setId(String id) {
            this.id = id;
            return this;
        }

        public testPojo(String id) {
            this.id = id;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestCyclicBarrierAdvance testCyclicBarrierAdvance = new TestCyclicBarrierAdvance();
        testCyclicBarrierAdvance.test1();
    }

    /**
     测试log:

     main主线程开启了两条子线程后,自己即将进入屏障点!@@@
     thread1线程开始处理业务.
     thread1线程完成业务处理,将要把处理结果放入容器.
     thread1线程处理业务和结果完毕,将进入屏障点.
     thread2线程开始处理业务.
     thread2线程完成业务处理,将要把处理结果放入容器.
     thread2线程处理业务和结果完毕,将进入屏障点.
     main主线通过了屏障点! 说明所有指定的线程都到达的屏障点,所以栅栏打开了,所有线程都应该通过了屏障点!即: 其他子线程完成了各自的业务@@@
     main开始获取其他线程处理完成的数据,数据已由各子线程完成聚拢,数据容器的大小为:2.@@@

     注意:
        测试结果与自己想的一样! 栅栏 CyclicBarrier 可以作为闭锁和 CompleteService的进阶使用!
     */
}
