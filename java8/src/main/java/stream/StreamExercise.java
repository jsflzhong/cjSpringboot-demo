package stream;

import org.assertj.core.util.Lists;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 对流的操作.
 * 分三种大类:
 *
 * 1.Intermediate：
 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
 *
 * 2.Terminal：
 * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
 *
 * 3.Short-circuiting：
 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
 */
public class StreamExercise {

    /**
     * 1.构造流的几种方式
     */
    public static void test1_createStream() {
        //1.直接流
        Stream<String> stream1 = Stream.of("1", "2", "3");

        //2.List生成流
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);
        Stream<Integer> stream2 = list.stream();

        //3.Array生成流
        Integer[] array = {1, 2, 3};
        Stream<Integer> stream3 = Arrays.stream(array);

        //4.Set生成流
        HashSet<String> set = new HashSet<>();
        Stream<String> stream4 = set.stream();
    }

    /**
     * 2.基本: 把流转换成为其他的数据结构.(取结果集)
     * 一个 Stream 只可以使用一次，下面用了多次,只是为了简洁得看.
     * 切记: 流可以转成很多种容器或String!
     */
    public static void test2_transStream(Stream<String> stream) {
        //1.流转Array
        String[] array = stream.toArray(String[]::new);

        //2.List
        List<String> list = stream.collect(Collectors.toList());

        //3.Set
        Set<String> set = stream.collect(Collectors.toSet());

        //4.String
        String s = stream.toString();
    }

    /**
     * 3.1."map"方法.
     * 作用: 用来"改变"容器里面的元素！
     *
     * @param list list
     */
    public static void test3_map(List<String> list) {
        //省略Optional判空

        List<String> resultList = list.stream().map(o -> o.toUpperCase()).collect(Collectors.toList());
        System.out.println(resultList);//[TEST1, TEST2, TEST3]
    }

    /**
     * 3.2."filter"方法.
     * 作用: 用来"找出"（过滤出）容器里面只符合条件的元素。
     *
     * @param list list
     */
    public static void test4_filter(List<String> list) {
        List<String> resultList = list.stream().filter(o -> "test1".equals(o)).collect(Collectors.toList());
        System.out.println(resultList);//[test1]
    }

    /**
     * 3.3."match"方法.
     * 作用: 用来判断集合中的元素中是否: 有,或没有,或都是 符合某个条件的元素.
     *
     * 有三种Match:
     * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     *
     * 作用: 该API可以用来, 判断容器中是否含有某个指定特征的元素. 返回boolean. 也挺好用.
     *
     * tested
     */
    public static void test5_match(List<String> list) {
        //是否有值为"test1"的元素， 哪怕只有1个也会返回true。
        boolean a = list.stream().anyMatch(o -> "test1".equals(o)); //true

        //list中的所有元素的值是否全部都是"test1"。
        boolean b = list.stream().allMatch(o -> "test1".equals(o)); //false

        //list中的所有元素的值，是否全都不等于"test1"。哪怕只有1个等于的，也会返回false.
        boolean c = list.stream().noneMatch(o -> "test1".equals(o)); //false

        System.out.println(a + "/" + b + "/" + c); //true/false/false
    }

    /**
     * 3.4."foreach"方法
     * 作用: 迭代容器
     */
    public static void test6_foreach(List<String> list) {
        //1.串行迭代
        list.stream().forEach(o -> System.out.println(o));

        System.out.println("===========================");

        //2.并行迭代
        //Java8的paralleStream用fork/join框架提供了并发执行能力。
        //注意: 该方法并非线程安全的! 下面新开了对于该方法的研究.
        list.parallelStream().forEach(System.out::println);
    }

    /**
     * 3.5.sorted
     * 排序容器
     *
     * 好处是,可以先用stream之后的其他操作API,先把list中的元素执行某种操作, 然后再排序.
     *
     * tested
     */
    public static void test7_sorted(List<String> list) {
        List<String> resultList = list.stream()
                .limit(2) //顺便用"limit"方法限定两个元素
                .sorted((o1, o2) -> o1.compareToIgnoreCase(o2))//自定义排序时的对比规则。
                .collect(Collectors.toList());

        System.out.println(resultList); //[tEst1, test2]
    }


    private static List<Integer> list1 = new ArrayList<>();
    private static List<Integer> list2 = new ArrayList<>();
    private static List<Integer> list3 = new ArrayList<>();
    private static Lock lock = new ReentrantLock();

    /**
     * 测试parallelStream的线程非安全性, 以及解决方案.
     */
    static void test8_ParallelStream() {
        //验证Java8的paralleStream是线程非安全的.
        test8_Foreach();

        //解决方案

    }

    /**
     * 验证Java8的paralleStream是线程非安全的.
     * 一个简单的例子,在下面的代码中采用stream的forEach接口对1-10000进行遍历，分别插入到3个ArrayList中。
     * 其中对第一个list的插入采用串行遍历，
     * 第二个使用paralleStream，
     * 第三个使用paralleStream的同时用ReentryLock对插入列表操作进行同步：
     *
     * 执行结果:
     * 串行执行的大小：10000
     * 并行执行的大小：9970 (该值每次执行的结果都不同)
     * 加锁并行执行的大小：10000
     *
     * 结论:
     * 在stackOverflow上找到了答案：
     *
     * https://codereview.stackexchange.com/questions/60401/using-java-8-parallel-streams
     *
     * https://stackoverflow.com/questions/22350288/parallel-streams-collectors-and-thread-safety
     *
     * 在上面两个问题的解答中，证实paralleStream的forEach接口确实不能保证同步，
     *
     * 同时也提出了解决方案：使用"collect接口"和"reduce接口"。
     */
    static void test8_Foreach() {

        IntStream.range(0, 10000).forEach(list1::add); //stream的foreach

        IntStream.range(0, 10000).parallel().forEach(list2::add); //stream的并行方法,验证的就是这个.

        IntStream.range(0, 10000).forEach(i -> {  //普通的foreach并加锁
            lock.lock();
            try {
                list3.add(i);
            }finally {
                lock.unlock();
            }
        });

        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + list2.size());
        System.out.println("加锁并行执行的大小：" + list3.size());
    }

    /**
     * 结果：
     * @@@new id:1tail
     * @@@new id:2tail
     * @@@map: {aaa=bbb}
     */
    static void test9_toMap() {
        A a1 = new A().setId("1");
        A a2 = new A().setId("2");

        List<A> list = Arrays.asList(a1, a2);
        Map<String, String> map = list.stream().peek(a -> {
            a.setId(a.getId() + "tail");
        }).collect(
                Collectors.toMap(A::func1, A::func2, (oldValue, newValue) -> newValue));

        list.forEach(a -> System.out.println("@@@new id:" + a.getId()));
        System.out.println("@@@map: " + map);
    }

    /**
     * 测试结果:
     * @@@The new id of B in A is :3
     */
    static void test10_asignThenGiveValue() {
        A a = new A().setId("1");
        B b = new B().setId("2");
        a.setbList(Arrays.asList(b));
        b.setId("3");//check if the value in A's B is updated.
        a.getbList().forEach(o -> System.out.println("@@@The new id of B in A is :" + o.getId()));
    }


    public static void main(String[] args) {
        //ArrayList<String> list = Lists.newArrayList("test1", "tEst2", "Test3");

        //test3_map(list);//[TEST1, TEST2, TEST3]

        //test4_filter(list);//[test1]

        //test5_match(list);//true/false/false

        //test6_foreach(list);

        //test7_sorted(list);//[tEst1, test2]

        //ArrayList<String> list2 = Lists.newArrayList("test1", "tEst2", "Test3", "test1");

        //test4_filter(list2); //[test1, test1]

        //test8_ParallelStream();


    }


    static class A {
        private String id;
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