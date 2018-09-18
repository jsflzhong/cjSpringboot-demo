package com.cj.concurrent.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by jsflz on 2018/8/10.
 * 测试:信号量Semaphore.
 * 作用:维护了一组许可证. 可用来做池子.例如线程池或DB连接池.提供阻塞的方法.
 * 也可以用 BlockingQueue 达到同样的效果.
 * <p>
 * 本例:
 * 用 信号量Semaphore 模拟创建一个:带边界的,阻塞式对象池
 * 思想: 监视器模式. 由 上层视窗 来控制边界和阻塞规则,由内部组合的底层java容器来实际存储数据.
 * <p>
 * API:
 * semaphore.acquire() - 从信号量中获取一个许可,如果许可证已经为空了,拿不到了,则本线程阻塞! 直到有富裕的许可证可以被拿到.
 * semaphore.release() - 释放一个许可证返回给信号量.
 *
 * @author cj
 */
public class TestSemaphore<T> {

    //组合的底层java容器,用来实际存储数据.
    private final Set<T> set;
    //上层视窗, 用来控制边界和阻塞规则.
    private final Semaphore semaphore;

    //构造器. 参数是池子的边界.
    public TestSemaphore(int bound) {
        //初始化容器为同步容器.
        this.set = Collections.synchronizedSet(new HashSet<T>());
        //初始化信号量,提供边界值.
        this.semaphore = new Semaphore(bound);
    }

    /**
     * 往池子中添加元素
     *
     * @param o 元素
     * @return true:添加成功; false:失败
     * @throws InterruptedException InterruptedException
     * @author cj
     */
    public boolean add(T o) throws InterruptedException {
        //视窗规则: 在往池子添加新元素之前,先找信号量获取许可证. 类似redis的setconx,不同作用,相似用法.
        System.out.println(Thread.currentThread().getName() + "线程开始获取许可,如果没有富裕许可可以获取,则本线程会进入阻塞...@@@");
        semaphore.acquire();
        System.out.println(Thread.currentThread().getName() + "线程结束获取许可,获取许可成功,解除本次的阻塞...@@@");
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            //返回: 本次是否添加新元素成功.
            return wasAdded;
        } finally {
            if (!wasAdded) {
                //视窗规则: 如果添加失败,则要释放本次获取的许可证,让池子有空位可以进行下一次的新元素的添加.
                semaphore.release();
            }
        }
    }

    /**
     * 从池子中移除元素
     *
     * @param o 元素
     * @return true: 移除成功; false:失败
     * @author cj
     */
    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            //视窗规则: 如果移除失败,则要释放本元素占用的许可证,让池子有空位可以进行下一次的新元素的添加.
            semaphore.release();
        }
        return wasRemoved;
    }

    public static void main(String[] args) throws InterruptedException {
        TestSemaphore<String> testSemaphore = new TestSemaphore<>(2);
        System.out.println("开始往池子里添加第1个元素");
        boolean wasAdded = testSemaphore.add("1");
        System.out.println("结束往池子里添加第1个元素,是否成功: " + wasAdded);
        System.out.println("开始往池子里添加第2个元素");
        testSemaphore.add("2");
        System.out.println("结束往池子里添加第2个元素,是否成功: " + wasAdded);
        System.out.println("开始往池子里添加第3个元素");
        testSemaphore.add("3");
        System.out.println("结束往池子里添加第3个元素,是否成功: " + wasAdded);

    }

    /**
     * 测试log:
     *
     开始往池子里添加第1个元素
     main线程开始获取许可,如果没有富裕许可可以获取,则本线程会进入阻塞...@@@
     main线程结束获取许可,获取许可成功,解除本次的阻塞...@@@
     结束往池子里添加第1个元素,是否成功: true
     开始往池子里添加第2个元素
     main线程开始获取许可,如果没有富裕许可可以获取,则本线程会进入阻塞...@@@
     main线程结束获取许可,获取许可成功,解除本次的阻塞...@@@
     结束往池子里添加第2个元素,是否成功: true
     开始往池子里添加第3个元素
     main线程开始获取许可,如果没有富裕许可可以获取,则本线程会进入阻塞...@@@

     注意:
        由于初始化信号量时,设定的许可证是2个,而主线程往里面放了三次元素,所以第三次时,在调用:semaphore.acquire()时，本线程被阻塞了.
        可以用子线程延迟模拟从池子中取出一个元素来释放一个许可,从而让主线程从阻塞中恢复过来并成功的放入第三个元素.
        本次略.
     */

}
