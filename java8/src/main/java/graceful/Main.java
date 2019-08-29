package graceful;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Main {

    public static void main(String[] args) {
        test_notNullAndEquals("aaa");

        //test_listNotNull();

        //test_paramAndFunctionalInterface();
    }

    /**
     * 1.判断字符串的 判空&&相等.
     *
     * @param name name
     */
    static void test_notNullAndEquals(String name) {
        //不完善的写法：
        if (name != null && name.equals("aaa")) {
            System.out.println("111");
        }

        //建议方案1：
        //当name为null时,此处也不会报空指针. 下行有测试.
        if (Objects.equals(name, "aaa")) {
            System.out.println("222");
        }

        //建议方案2：
        if (StringUtils.equals(name, "aaa")) {
            System.out.println("333");
        }

        //建议方案3-忽略大小写：
        if (StringUtils.endsWithIgnoreCase(name, "AAA")) {
            System.out.println("444");
        }

        //以上三种方案都不会报空指针.

        //测用null和==， 是不会出null pointer的.
        name = null;
        boolean b = null == "1";
        System.out.println("@@@result: " + b);

        //等于 if(obj != null);
        if (Objects.nonNull(name)) {
            System.out.println("000");
        }
    }

    /**
     * 2.对List的判空.
     */
    static void test_listNotNull() {
        ArrayList<String> list1 = null;

        //List<String> list1 = Arrays.asList("1", "2");

        //不完善的写法：
        if (!(list1 == null || list1.isEmpty())) {
            System.out.println("111");
        }

        //建议方案1： (java7)
        if (CollectionUtils.isNotEmpty(list1)) {
            System.out.println("222");
        }

        //建议方案2: (java8)
        Optional.ofNullable(list1).ifPresent(o -> System.out.println("333"));
    }

    //3.对数组判空
    static void test_arrayNotNull() {
        String[] a = null;
        ArrayUtils.isEmpty(a);
    }



    /**
     * ***当多个地方用到了: 获取数据的动作 + 拿着这个数据来进行有共性的一系列逻辑 的时候,可以把动作封装成函数接口, 把可变的值当做参数传入.s
     */
    static void test_paramAndFunctionalInterface() {
        handleUserList("changeableName", () -> userDao_getUserList());
    }

    /**
     * 模拟从DB 获取数据.
     * 这个部分, 每个类似这里的调用方, 都可以用不同的方式和动作来获取这个数据集.
     */
    static List<User> userDao_getUserList() {
        return Arrays.asList(new User().setId(1), new User().setId(2));
    }

    /**
     * 用自定义函数接口, 封装一套动作和逻辑.
     * <p>
     * ***当多个地方用到了: 获取数据的动作 + 拿着这个数据来进行有共性的一系列逻辑 的时候,可以把动作封装成函数接口, 把可变的值当做参数传入.
     * <p>
     * ***类似Caffeine, "提供数据"的动作转移到调用方来提供, "处理数据"的动作由这里统一进行, 可变的部分由形参传入.
     *
     * @param changeable   可变的部分.
     * @param dateProvider 自定义的函数接口.
     */
    static void handleUserList(String changeable, DataProvider<User> dateProvider) {
        //执行获取用户对象的集合的动作. 具体的获取方式, 由调用方自行决定.
        List<User> userList = dateProvider.provide();

        //处理数据集的逻辑.
        //该部分包含可变的值, 由形参传入.
        Optional.ofNullable(userList).ifPresent(list -> list.forEach(user -> {
            User user2 = new User();
            user2.setId(user.getId());
            //每个调用方可能不同的部分, 由参数传入.
            user2.setName(changeable);
            System.out.println(user2.getId());
        }));
    }

    /**
     * 自定义一个空参和1返回值的FI.
     * 用来定义: 获取User对象集合的动作.
     * <p>
     * 该接口可以优化, 应该与业务分离, 用泛型使其与User分离. 下面有.
     */
    @FunctionalInterface
    private interface UserDataProvider {
        //获取User的数据集
        public List<User> provide();
    }

    /**
     * 对UserDataProvider的优化.
     *
     * @param <T>
     */
    @FunctionalInterface
    private interface DataProvider<T> {
        //获取User的数据集
        public List<T> provide();
    }

    /**
     * 方便测试用的pojo
     */
    public static class User {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public User setId(int id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public User setName(String name) {
            this.name = name;
            return this;
        }
    }
}

