package interfaceNew;

import com.sun.org.apache.xpath.internal.operations.Bool;
import interfaceNew.Exception.BusinessException;
import interfaceNew.Exception.CheckedException;
import interfaceNew.functionalInterface.FunctionalInterface1;
import interfaceNew.functionalInterface.JudgeAndExecute;
import interfaceNew.normalInterface.SubClass1;
import interfaceNew.normalInterface.SubClass2;
import interfaceNew.normalInterface.SuperInterface;
import interfaceNew.parameter.BusinessParam;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main implements JudgeAndExecute {

    public static void main(String[] args) {

        //1.注意:实现接口的类或者子接口不会继承父接口中的静态方法.
        System.out.println("@@@@@@@@@ Test1 @@@@@@@@@@");
        SuperInterface.staticFunction();//@@@This is the static function in super interface.

        //2.子类可以直接继承父接口中的default方法.
        System.out.println("@@@@@@@@@ Test2 @@@@@@@@@@");
        SubClass1 subClass1 = new SubClass1();
        subClass1.defaultFunction();//@@@This is the default function in super interface.

        //3.子类也可以覆盖父接口中的default方法.
        System.out.println("@@@@@@@@@ Test3 @@@@@@@@@@");
        SubClass2 subClass2 = new SubClass2();
        subClass2.defaultFunction();//@@@This is the default function in subClass2.

        //4.测试自定义的functinal interface函数式接口.
        System.out.println("@@@@@@@@@ Test4 @@@@@@@@@@");
        testFunctionalInterface1("@@@testParam", (o) -> System.out.println(o));//@@@testParam

        //5.测试Java内置的函数式接口:Consumer
        System.out.println("@@@@@@@@@ Test5 @@@@@@@@@@");
        testConsumer("@@@testConsumer", (o) -> System.out.println(o));//@@@testConsumer

        //6.测试Java内置的函数式接口:Consumer中的andThen()方法.
        System.out.println("@@@@@@@@@ Test6 @@@@@@@@@@");
        call_testConsumerAndThen();

        //7.测试Java内置的函数式接口:Function中的R apply(T t)方法
        System.out.println("@@@@@@@@@ Test7 @@@@@@@@@@");
        call_testApplay();

        //8.测试Java内置的函数式接口predicate:中的boolean test(T t)方法
        System.out.println("@@@@@@@@@ Test8 @@@@@@@@@@");
        call_testPredicate_test();

        System.out.println("@@@@@@@@@ Test9 @@@@@@@@@@");
        new Main().callRun();

    }

    /**
     * 测试自定义的functinal interface.
     * 可以把functionalInterface接口作为方法的形参.
     *
     * @param s                    functionalInterface1中抽象方法的参数.
     * @param functionalInterface1 自定义的函数式接口
     *                             如果某天需求变了，我不想输出这句话了，想输出别的，那么直接替换text就好了。
     *                             函数式编程是没有副作用的，最大的好处就是函数的内部是无状态的，既输入确定输出就确定。
     *                             由于functionalInterface1接口中的抽象方法只有一个参数,所以调用时给个参数.例如上面main中的:(o) -> System.out.println(o)
     */
    static void testFunctionalInterface1(String s, FunctionalInterface1 functionalInterface1) {
        //直接调用传入的形参,即自定义的函数式接口.
        functionalInterface1.print(s);
    }

    /**
     * 测试Java内置的函数式接口:Consumer 的accept()方法
     * 抽象方法： void accept(T t)，接收一个参数进行消费，但无返回结果。
     *
     * @param s
     * @param consumer
     */
    static void testConsumer(String s, Consumer<String> consumer) {
        consumer.accept(s);
    }

    /**
     * 测试Java内置的函数式接口:Consumer 的andThen()方法.
     * 使用andThen方法,可以同时连接"两个消费者",分别调用这两个消费者里面的accept来消费字符串.
     * 常用来分割处理集合中的字符串.
     * 测试结果:
     * 张三
     * 男
     * 李四
     * 女
     */
    static void call_testConsumerAndThen() {
        List<String> list = new ArrayList<String>();
        list.add("张三,男");
        list.add("李四,女");
        testConsumerAndThen(
                list,
                (s1) -> System.out.println(s1.split(",")[0]), //分割消费字符串中的两个部分.
                (s2) -> System.out.println(s2.split(",")[1]));
    }

    /**
     * @param list
     * @param consumer1
     * @param consumer2
     */
    static void testConsumerAndThen(List<String> list, Consumer<String> consumer1, Consumer<String> consumer2) {
        //可以这样定义一个函数式接口.
        //Consumer<String> consumer1 = (o) -> System.out.println("定义函数式接口1" + o);
        //Consumer<String> consumer2 = (o) -> System.out.println("定义函数式接口2" + o);

        //先调用consumer1中的accpet来消费, 再调用consumer2中的accept消费.
        //上面的两个o,就是下面accept传入的参数.
        //consumer1.andThen(consumer2).accept("结束");

        //使用andThen方法,可以同时连接"两个消费者",分别调用这两个消费者里面的accept来消费字符串.
        //注意别忘记最后的accept()
        for (String s : list) {
            consumer1.andThen(consumer2).accept(s);
        }

    }

    static void testInsertDB() {

    }

    /**
     * 测试Java内置的函数式接口:Supplier 的 get()方法.
     */
    static void testSupplier(Supplier<String> supplier) {
        supplier.get();
    }

    /**
     * 测试Java内置的函数式接口:Function中的R apply(T t)方法
     * 模拟Caffeine缓存的用法
     */
    static void call_testApplay() {
        String value = "value";//模拟从DB读取到数据的操作.
        String key = "key"; //这个打开的话,测试结果是"value".
        //String key = "key1"; //这个打开的话,测试结果是"value1", 因为可以从下面的缓存中拿到该key对应的value;
        String result = testApplay((key1) -> value, key);
        System.out.println("@@@result:" + result);

    }

    static String testApplay(Function<String, String> function, String key) {
        //模拟缓存
        HashMap<String, String> cache = new HashMap<>();
        cache.put("key1", "value1");
        //如果缓存中有,就从缓存中拿值后返回, 否则就用传入的方法取值(例如从DB取值)
        return Optional.ofNullable(cache.get(key)).orElse(function.apply(key));
    }

    static void call_testPredicate_test() {
        //String testValue = "test"; //测试结果: true
        String testValue = "test1"; //测试结果: false
        //String testValue = ""; //测试结果: false
        //String testValue = null; //测试结果: @@@error... get!!!

        Boolean result = testPredicate_test(o -> {
            if(StringUtils.isEmpty(testValue)) {
                throw new RuntimeException("@@@error... get!!!");
            }
            return testValue.equals(o);
        });
        System.out.println("@@@result: " + result);
    }

    static Boolean testPredicate_test(Predicate<String> s) {
        String testValue = "test";
        Optional.ofNullable(s).orElse((e) -> {
            throw new RuntimeException("@@@error.." + e);
        });
        return s.test(testValue);
    }

    /**
     * 测试自定义的if/else接口
     */
    void callRun() {
        Boolean flag = true;
        BusinessParam businessParam = new BusinessParam()
                .setOrderId("1").setTime(new Date()).setAmount(2);

        //调用if/else函数式接口
        //该this决定了本方法不能是static.否则无法调用.
        //2,3方法都无返回值,决定了所调用的上层接口中的函数式接口的方法,也必须没有返回值.否则报错.
        //这也是为什么要在父接口中还要自定义一个函数式接口,而不适用内置的四大函数式接口的原因.
        this.run(flag, () -> testHandleParam1(businessParam), () -> testHandleParam2(businessParam));

        //调用if函数式接口
        this.run(flag, () -> testHandleParam1(businessParam));

        //调用并处理unchecked异常
        try {
            this.run(flag, () -> testHandleParam3(businessParam));
        } catch(BusinessException e) {
            System.out.println("@@@catch businessException!!!...:" + e.getMessage());
        }

        //调用并处理checked异常
        try {
            //这行由于所调用的testHandleParam4()方法会向上层抛出异常,所以调用的函数接口interface本身的抽象方法就需要抛出异常
            //否则这里内部还需要try catch一次.
            this.runWithE(flag, () -> testHandleParam4(businessParam));
        }catch (CheckedException e) {
            //这里可以正常抓到上面testHandleParam4方法中抛出来的异常了.
            System.out.println("@@@catch checkedException!!!...:" + e.getMessage());
        }

    }

    /**
     * 模拟对业务参数的操作1
     * @param param
     */
    static void testHandleParam1(BusinessParam param) {
        System.out.println("@@@testHandleParam1()... orderId : " + param.getOrderId());
    }

    /**
     * 模拟对业务参数的操作2
     * @param param
     */
    static void testHandleParam2(BusinessParam param) {
        System.out.println("@@@testHandleParam2()... orderId : " + param.getOrderId());
    }

    /**
     * 模拟对业务参数的操作3, 抛异常
     * @param param
     */
    static void testHandleParam3(BusinessParam param) {
        System.out.println("@@@testHandleParam3()... orderId : " + param.getOrderId());
        if(1==1){
            throw new BusinessException("@@@Test exception1...");
        }
    }

    /**
     * 模拟对业务参数的操作3, 抛异常(Checked)
     * @param param
     */
    static void testHandleParam4(BusinessParam param) throws CheckedException {
        System.out.println("@@@testHandleParam4()... orderId : " + param.getOrderId());
        if(1==1){
            throw new CheckedException("@@@Test exception2...");
        }
    }

}



