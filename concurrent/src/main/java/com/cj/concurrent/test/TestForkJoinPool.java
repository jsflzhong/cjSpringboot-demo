package com.cj.concurrent.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

public class TestForkJoinPool {

    public static void main(String[] args) {
        testForkJoinPool();
    }

    /**
     * 测试:ForkJoinPool
     *
     * 测试结果:
     * @@@list中的元素为基础类型,原list的元素为:1
     * @@@list中的元素为基础类型,原list的元素为:2
     * @@@list中的元素为基础类型,原list的元素为:3
     * @@@num: 2
     * @@@num: 3
     * @@@num: 4
     * @@@list中的元素为基础类型,新list的元素为:1
     * @@@list中的元素为基础类型,新list的元素为:2
     * @@@list中的元素为基础类型,新list的元素为:3
     * ====================
     * @@@listA中的元素为对象类型,原list的元素为:1
     * @@@listA中的元素为对象类型,原list的元素为:2
     * @@@a.id: 1tail
     * @@@a.id: 2tail
     * @@@listA中的元素为对象类型,新list的元素为:1tail
     * @@@listA中的元素为对象类型,新list的元素为:2tail
     *
     * 测试结论:
     * 1.forkJoinTasks.forEach(...)执行后,才会真正用多线程执行任务.
     * 2.并不是顺序执行的,因为是多线程.
     * 3.任务中,不会改变原list中的基本类型,但是会改变原list中的对象类型.
     */
    static void testForkJoinPool() {
        ForkJoinPool pool = new ForkJoinPool();

        List<Integer> list = Arrays.asList(1, 2, 3);
        list.forEach(num -> System.out.println("@@@list中的元素为基础类型,原list的元素为:" + num));

        List<? extends ForkJoinTask<?>> forkJoinTasks = list.stream()
                .map(num -> pool.submit(() -> testFunc(num)))
                .collect(Collectors.toList());
        forkJoinTasks.forEach(ForkJoinTask::join); //这一行执行完后,才会真正用多线程去执行任务:testFunc();

        list.forEach(num -> System.out.println("@@@list中的元素为基础类型,新list的元素为:" + num));

        System.out.println("====================");

        List<A> listA = Arrays.asList(new A().setId("1"),new A().setId("2"));
        listA.forEach(num -> System.out.println("@@@listA中的元素为对象类型,原list的元素为:" + num.getId()));

        List<? extends ForkJoinTask<?>> forkJoinTasks2 = listA.stream()
                .map(a -> pool.submit(() -> testFunc2(a)))
                .collect(Collectors.toList());
        forkJoinTasks2.forEach(ForkJoinTask::join);

        listA.forEach(num -> System.out.println("@@@listA中的元素为对象类型,新list的元素为:" + num.getId()));

    }

    static void testFunc(Integer num) {
        num += 1;
        System.out.println("@@@num: " + num);
    }

    static void testFunc2(A a) {
        a.setId(a.getId() + "tail");
        System.out.println("@@@a.id: " + a.getId());
    }

    static class A {
        private String id;
        @Valid
        @NotEmpty
        List<B> bList;

        public String getId() {
            return id;
        }

        public A setId(String id) {
            this.id = id;
            return this;
        }

        public List<B> getbList() {
            return bList;
        }

        public void setbList(List<B> bList) {
            this.bList = bList;
        }

        String func1() {
            return "aaa";
        }

        String func2() {
            return "bbb";
        }
    }

    static class B {
        private String id;

        public String getId() {
            return id;
        }

        public B setId(String id) {
            this.id = id;
            return this;
        }
    }
}
