package com.cj.concurrent.test;


import com.cj.concurrent.exception.BusinessException;
import com.cj.concurrent.exception.ExceptionLauncher;
import com.cj.concurrent.exception.ExceptionLauncherImpl;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Jian.Cui on 2018/9/27.
 */
public class TestCompletableFuture {

    private static ExceptionLauncher exceptionLauncher = new ExceptionLauncherImpl();

    //自定义线程池
    private static ExecutorService executorService = Executors.newWorkStealingPool(150);


    /**
     * 注意,这里模拟的是"网络请求"! 所以多线程那边,主线程在阻塞等待时,
     * 必须调用有timeout的API! 否则容易造成很多线程僵死,导致最后服务不可用!!!
     *
     * @return
     */
    public static String testHandler1() {
        try {
            System.out.println("@@@testHandler1 要阻塞了......");
            Thread.sleep(1000);
            System.out.println("@@@testHandler1 苏醒了......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "testHandler1";
    }

    /**
     * 注意,这里模拟的是"网络请求"! 所以多线程那边,主线程在阻塞等待时,
     * 必须调用有timeout的API! 否则容易造成很多线程僵死,导致最后服务不可用!!!
     * 带参数版
     *
     * @return
     */
    public static String testHandler2(String param) {
        try {
            //模拟业务处理...
            System.out.println("@@@testHandler2 要阻塞了......");
            Thread.sleep(1000);
            System.out.println("@@@testHandler2 苏醒了......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param;
    }

    public static String test1() {
        return "test1";
    }

    /**
     * 初级版:
     * 这个例子只用一个线程处理了一个业务方法,下面第的方法会处理一个list.
     * Tested
     *
     * @return
     * @throws BusinessException
     */
    public static List<String> test2() throws BusinessException {
        //用来存放聚拢数据的容器.
        ArrayList<String> stringList = new ArrayList<>();

        //要拿这个返回值! 虽然方法里面已经用API和上面的list完成了数据的聚拢,
        // 但是,如果里面调用的业务方法中,处理的是网络请求,那么,主线程必须调用有timeout的阻塞方法,
        // 防止对方网络接口出问题而导致本地服务线程的僵死.
        CompletableFuture<String> stringFuture = CompletableFuture
                .supplyAsync(TestCompletableFuture::testHandler1) //用多线程调用处理业务的方法.
                .whenComplete((n, throwable) -> stringList.add(n)); //聚合数据到List中

        try {
            //主线程等上面所有子线程返回.如果超过指定时间(20秒本次)还没全部返回,则抛异常:TimeoutException,在下面处理了.
            stringFuture.allOf(stringFuture).get(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            exceptionLauncher.throwBusinessException("协议处理器线程处理中断", e);
        } catch (ExecutionException e) {
            exceptionLauncher.throwBusinessException("协议处理器线程执行过程中发生异常", e);
        } catch (TimeoutException e) {
            if (!stringFuture.isDone()) stringFuture.cancel(true);//对网络请求的超时异常的处理!
            exceptionLauncher.throwBusinessException("协议处理器执行超时", e);
        }

        System.out.println("@@@主线程继续执行......");
        return stringList;
    }

    /**
     * 用CompletableFuture处理一个list.
     * 高级版.
     * Tested
     *
     * @return
     * @throws BusinessException
     */
    public static List<String> test3() throws BusinessException {

        //模拟源数据的容器.可以想象成跑批任务捞取的300个将要处理的代付数据.
        ArrayList<String> sourceList = Lists.newArrayList("e1", "e2", "e3");

        //模拟结果数据. 用来存放用子线程处理后的聚拢数据的容器.
        ArrayList<String> stringList = new ArrayList<>();

        //要拿这个返回值! 虽然方法里面已经用API和上面的list完成了数据的聚拢,
        //但是,如果里面调用的业务方法中,处理的是网络请求,那么,主线程必须调用有timeout的阻塞方法,
        // 防止对方网络接口出问题而导致本地服务线程的僵死.
        //而主线程阻塞等待所有子线程返回的手段,就是必须拿到子线程这里返回的CompletableFuture对象,并调用其方法,才能做到!
        CompletableFuture[] stringFutures = sourceList.stream().map(sourceData ->
                CompletableFuture.supplyAsync(() -> testHandler2(sourceData)) //用CompletableFuture的方式开启多线程,调用处理业务的方法.
                        .whenComplete((n, throwable) -> stringList.add(n))//当业务处理结束并返回后,聚合返回的数据到List中.
        ).toArray(CompletableFuture[]::new);//如果不加这个toArray这块,那么整体就是list.stream().map()返回的结果,那么就肯定不是我想要的CompletableFuture了.我需要返回这个,然后下面主线程用这个返回的future来等待子线程全部执行完成.

        try {
            //主线程等上面所有子线程返回.如果超过指定时间(20秒本次)还没全部返回,则抛异常:TimeoutException,在下面处理了.
            CompletableFuture.allOf(stringFutures).get(20, TimeUnit.SECONDS); //注意这次的是个静态方法,而不能用上面的结果点出来API了.
        } catch (InterruptedException e) { //被中断异常
            Thread.currentThread().interrupt();
            exceptionLauncher.throwBusinessException("@@@处理器线程处理中断", e);
        } catch (ExecutionException e) { //其他异常
            exceptionLauncher.throwBusinessException("@@@处理器线程执行过程中发生异常", e);
        } catch (TimeoutException e) { //超时异常
            //对网络请求的超时异常的处理!
            //用stream流化上面返回的数组并迭代,用filter过滤出里面没有完成的网络请求的线程,并cancel掉它.
            Arrays.stream(stringFutures).filter(o -> !o.isDone()).forEach(o -> o.cancel(true));
            //然后抛异常,中断本次业务处理.
            exceptionLauncher.throwBusinessException("@@@处理器执行超时", e);
        }

        System.out.println("@@@主线程继续执行......");
        return stringList;
    }

    /**
     * 用CompletableFuture处理一个list.
     * 高级版2--指定自定义的线程池.
     * CompletableFuture如果不指定线程池,那么它使用的是java1.8内置的一个静态线程池,线程数的CPU的核数.
     * 由于默认的线程数偏少,所以可以自己指定线程池.
     * Tested
     *
     * @return
     * @throws BusinessException
     */
    public static List<String> test4() throws BusinessException {

        //模拟源数据的容器.可以想象成跑批任务捞取的300个将要处理的代付数据.
        ArrayList<String> sourceList = Lists.newArrayList("e1", "e2", "e3");

        //模拟结果数据. 用来存放用子线程处理后的聚拢数据的容器.
        ArrayList<String> stringList = new ArrayList<>();

        //要拿这个返回值! 虽然方法里面已经用API和上面的list完成了数据的聚拢,
        //但是,如果里面调用的业务方法中,处理的是网络请求,那么,主线程必须调用有timeout的阻塞方法,防止对方网络接口出问题而导致本地服务线程的僵死.
        //而主线程阻塞等待所有子线程返回的手段,就是必须拿到子线程这里返回的CompletableFuture对象,并调用其方法,才能做到!
        CompletableFuture[] stringFutures = sourceList.stream().map(sourceData ->
                CompletableFuture.supplyAsync(() -> testHandler2(sourceData),executorService) //用CompletableFuture的方式开启多线程,调用处理业务的方法.此处指定的自定义的线程池!
                        .whenComplete((n, throwable) -> stringList.add(n))//当业务处理结束并返回后,聚合返回的数据到List中.
        ).toArray(CompletableFuture[]::new);//如果不加这个toArray这块,那么整体就是list.stream().map()返回的结果,那么就肯定不是我想要的CompletableFuture了.我需要返回这个,然后下面主线程用这个返回的future来等待子线程全部执行完成.

        try {
            //主线程等上面所有子线程返回.如果超过指定时间(20秒本次)还没全部返回,则抛异常:TimeoutException,在下面处理了.
            CompletableFuture.allOf(stringFutures).get(20, TimeUnit.SECONDS); //注意这次的是个静态方法,而不能用上面的结果点出来API了.
        } catch (InterruptedException e) { //被中断异常
            Thread.currentThread().interrupt();
            exceptionLauncher.throwBusinessException("@@@处理器线程处理中断", e);
        } catch (ExecutionException e) { //其他异常
            exceptionLauncher.throwBusinessException("@@@处理器线程执行过程中发生异常", e);
        } catch (TimeoutException e) { //超时异常
            //对网络请求的超时异常的处理!
            //用stream流化上面返回的数组并迭代,用filter过滤出里面没有完成的网络请求的线程,并cancel掉它.
            Arrays.stream(stringFutures).filter(o -> !o.isDone()).forEach(o -> o.cancel(true));
            //然后抛异常,中断本次业务处理.
            exceptionLauncher.throwBusinessException("@@@处理器执行超时", e);
        }

        System.out.println("@@@主线程继续执行......");
        return stringList;
    }


    /**
     * 测试用两个CompletableFuture(CompletionStage)来分别读取数据,然后combine到一起.
     */
    private static CompletionStage<List<String>> getTwoJokes() {
        final CompletionStage<String> firstJokeAsync = CompletableFuture.supplyAsync(() -> getChuckNorrisJoke());
        final CompletionStage<String> secondJokeAsync = CompletableFuture.supplyAsync(() -> getChuckNorrisJoke());

        return firstJokeAsync.thenCombine(secondJokeAsync, (firstData, secondData) -> {
            ArrayList<String> strings = Lists.newArrayList(firstData, secondData);
            return strings;
        });
    }

    public static void testCombineCompletionStages()  throws BusinessException{
        //#################getTwoJokes#################
        final CompletionStage<Void> completionStage = getTwoJokes()
                .thenAccept(twoJokes ->
                        twoJokes.forEach(n ->
                                System.out.println(String.format("joke: %s",n))));
        try {
            CompletableFuture.allOf(completionStage.toCompletableFuture()).get(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) { //被中断异常
            Thread.currentThread().interrupt();
            exceptionLauncher.throwBusinessException("@@@处理器线程处理中断", e);
        } catch (ExecutionException e) { //其他异常
            exceptionLauncher.throwBusinessException("@@@处理器线程执行过程中发生异常", e);
        } catch (TimeoutException e) { //超时异常
            //对网络请求的超时异常的处理!
            //用stream流化上面返回的数组并迭代,用filter过滤出里面没有完成的网络请求的线程,并cancel掉它.
            if (!completionStage.toCompletableFuture().isDone()) {
                //然后抛异常,中断本次业务处理.
                exceptionLauncher.throwBusinessException("@@@处理器执行超时", e);
            }
        }
    }

    private static String getChuckNorrisJoke() {
        //模拟网络请求.
        return "abc";
    }

    public static void main(String[] args) throws BusinessException {

        //#################test3#################
        /*System.out.println("@@@主线程开始执行......");
        List<String> strings = test3();
        strings.stream().forEach(n -> System.out.println("@@@list中的元素:" + n));*/

        //testCombineCompletionStages();

        List<String> strings = test4();
        strings.forEach(System.out::println);

    }

    /**
     * log:
     *
     * test3:
     @@@主线程开始执行......
     @@@testHandler2 要阻塞了......
     @@@testHandler2 要阻塞了......
     @@@testHandler2 要阻塞了......
     @@@testHandler2 苏醒了......
     @@@testHandler2 苏醒了......
     @@@testHandler2 苏醒了......
     @@@主线程继续执行......
     @@@list中的元素:e1
     @@@list中的元素:e2
     @@@list中的元素:e3


     testCombineCompletionStages()
     joke: abc
     joke: abc


     test4:
     @@@testHandler2 要阻塞了......
     @@@testHandler2 要阻塞了......
     @@@testHandler2 要阻塞了......
     @@@testHandler2 苏醒了......
     @@@testHandler2 苏醒了......
     @@@testHandler2 苏醒了......
     @@@主线程继续执行......
     e1
     e3
     e2
     */
}
