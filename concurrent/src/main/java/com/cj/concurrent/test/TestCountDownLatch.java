package com.cj.concurrent.test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jsflz on 2018/8/10.
 * 测试:CountDownLatch
 * 作用:一条线程A调用await()进入阻塞,等其他线程都调用闭锁的countDown()把闭锁内的计数器清零后,该线程A醒来继续执行.
 * API:
 *  countDownLatch.await() - 阻塞当前线程,直到countDownLatch中的闭锁计数器清零后才会醒来.
 *  countDownLatch.countDown() - 把闭锁的计数器减一.
 */
public class TestCountDownLatch {

    /**
     * 测试闭锁.
     * 这里有三个线程（main，thread1，thread2）,
     * 其中main线程将调用countDownLatch的await方法去等待(阻塞),
     * 等待另外两个线程的业务操作的结束,分别调用countDownLatch的countDown方法将闭锁的计数器-1,
     * 当闭锁内部的计数器被清零后,主线程解除阻塞,继续执行.
     * 应用场景:
     *  1.多线程操作DB,所有子线程操作结束都返回数据后,主线程汇总数据返回.
     *  2.多线程操作Excel.
     *  3.定时跑批任务.
     * 附加:
     *  该方式,也就是闭锁,其实可以用更新的技术代替:ExecutorCompletionService.
     *
     * @throws InterruptedException InterruptedException
     * @author cj
     */
    private static void test1() throws InterruptedException {

        //由主线程新建计数器为2的闭锁,为了显示出被打开的线程,这里重写了await方法.
        //为了减少复杂性,把日志的打印工作,放在下面主线程调用闭锁的await()的下一行打印.
        CountDownLatch countDownLatch = new CountDownLatch(2); /*{
            @Override
            public void await() throws InterruptedException {
                super.await();
                System.out.println(Thread.currentThread().getName() + " count down is ok");
            }
        };*/

        //实际业务中,子线程的创建和执行,可以在for循环中进行.这里为了清晰所以分别创建.

        //由主线程新建子线程1.
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //业务处理代码写在这里...
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 执行完毕,将要把闭锁的计数器-1.");
                countDownLatch.countDown(); //把闭锁的计数器减一.
                System.out.println(Thread.currentThread().getName() + " 已把闭锁的计数器-1,当前的计数器为:" + countDownLatch.getCount());
            }
        }, "thread1");

        //由主线程新建子线程2.
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //业务处理代码写在这里...
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 执行完毕,将要把闭锁的计数器-1.");
                countDownLatch.countDown();//把闭锁的计数器减一.
                System.out.println(Thread.currentThread().getName() + " 已把闭锁的计数器-1,当前的计数器为:" + countDownLatch.getCount());
            }
        }, "thread2");

        //由主线程开启两条子线程.
        thread1.start();
        thread2.start();

        //主线程调用闭锁的方法后,会卡在这里等,等上面闭锁的计数器(被两条子线程分别-1)清零后才继续下去.
        System.out.println(Thread.currentThread().getName() + " thread is ok,主线将进入阻塞,等待闭锁的计数器清零后才会醒来.");
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " thread is ok,主线程醒了.");
    }

    public static void main(String[] args) throws InterruptedException {
        TestCountDownLatch.test1();
    }

    /**
     测试log:

     main thread is ok,主线将进入阻塞,等待闭锁的计数器清零后才会醒来.
     thread1 执行完毕,将要把闭锁的计数器-1.
     thread1 已把闭锁的计数器-1,当前的计数器为:1
     thread2 执行完毕,将要把闭锁的计数器-1.
     main thread is ok,主线程醒了.
     thread2 已把闭锁的计数器-1,当前的计数器为:0

     注意:
        当最后一个子线程调用 countDownLatch.countDown() 把闭锁的计数器清零后,的第一时间内,主线程就可能醒来.
        可能会把该子线程清零代码的下一行代码,给切入了.
     */
}
