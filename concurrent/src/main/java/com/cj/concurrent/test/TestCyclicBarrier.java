package com.cj.concurrent.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by jsflz on 2018/8/10.
 * 测试:栅栏 CountDownLatch
 * 作用:让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
 *      可以把这个屏障点想象成:每个子线程处理完业务后,而不是处理业务前. 这样就达到了闭锁或CompletionService的效果! 并且不用循环主线程来聚拢数据!
 * API:
 *  await() - 阻塞当前线程,直到所有线程(构造器指定数量)都达到屏障点后,所有被该点阻塞的线程会一起醒来.
 */
public class TestCyclicBarrier {

    /**
     * 测试:栅栏 CyclicBarrier
     * 这里有三个线程（main，thread1，thread2）,
     * 其中main线程将初始化一个栅栏(栅栏中线程数量设置为3),然后和两个 子线程一起调用await()进入阻塞.
     * 当三个线程都进入该屏障点后,栅栏会打开,他们会一起继续执行.
     * 应用场景:
     *  1.多线程操作DB,所有子线程操作结束都返回数据后,主线程汇总数据返回.
     *  2.多线程操作Excel.
     *  3.定时跑批任务.
     * 附加:
     *  该方式,也就是栅栏,其实可以用其他的技术代替: 闭锁CountDownLatch 或 CompletionService. 只不过栅栏感觉更简便!!
     *
     * @throws InterruptedException InterruptedException
     * @author cj
     */
    private static void test1() throws InterruptedException {

        //由主线程新建一个栏内线程数为3的栅栏.
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        //实际业务中,子线程的创建和执行,可以在for循环中进行.这里为了清晰所以分别创建.

        //由主线程新建子线程1.
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //业务处理代码写在这里...
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "线程处理业务完毕,将进入屏障点.");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "通过了屏障点!");
            }
        }, "thread1");

        //由主线程新建子线程2.
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //业务处理代码写在这里...
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "线程处理业务完毕,将进入屏障点.");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "通过了屏障点!");
            }
        }, "thread2");

        //由主线程开启两条子线程.
        thread1.start();
        thread2.start();

        try {
            System.out.println(Thread.currentThread().getName() + "主线程开启了两条子线程,即将进入屏障点!@@@");
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + "主线通过了屏障点! 说明所有指定的线程都到达的屏障点,所以栅栏打开了,所有线程都应该通过了屏障点!!!@@@");
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestCyclicBarrier.test1();
    }

    /**
     测试log:

     main主线程开启了两条子线程,即将进入屏障点!@@@
     thread1线程处理业务完毕,将进入屏障点.
     thread2线程处理业务完毕,将进入屏障点.
     thread2通过了屏障点!
     thread1通过了屏障点!
     main主线通过了屏障点! 说明所有指定的线程都到达的屏障点,所以栅栏打开了,所有线程都应该通过了屏障点!!!@@@

     注意:
        当最后一个子线程调用 countDownLatch.countDown() 把闭锁的计数器清零后,的第一时间内,主线程就可能醒来.
        可能会把该子线程清零代码的下一行代码,给切入了.
     */
}
