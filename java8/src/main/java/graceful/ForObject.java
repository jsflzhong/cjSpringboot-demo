package graceful;

import java.util.Objects;
import org.springframework.util.Assert;

import Common.User;

public class ForObject {

    public static void main(String[] args) {
        test_object();
    }

    /**
     * 推荐方式1 - 判断非空
     * 推荐方式2 - 判断是空
     * 推荐方式3 - 如果是空,就抛异常.(注意: 是spring的方法, 但抛的是java1.8自己的异常! 上层代码要注意捕获这个运行时异常然后处理!
     *
     * 运行结果:
     *      New way 2 - is null
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...
     *
     */
    public static void test_object() {
        try {
            Common.User user = null;

            //不推荐的老式的做法
            if(user != null) {
                System.out.println("Old way.");
            }

            //1.推荐方式1 - 判断非空
            if (Objects.nonNull(user)) {
                System.out.println("New way 1 - not null");
            }

            //2.推荐方式2 - 判断是空
            if (Objects.isNull(user)) {
                System.out.println("New way 2 - is null");
            }

            //3.推荐方式3 - 如果是空,就抛异常.(注意,是spring的方法,抛的是java1.8自己的异常!
            // 虽然是运行时异常, 但上层代码注意要捕获它然后处理: IllegalArgumentException
            // 这种方式比起在上面的if中手动写抛出异常,可以至少省出两行代码空间.
            Assert.notNull(user, "@@@user is null!");
            System.out.println("由于上行代码抛异常了,所以这里不会被执行到.");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...");
        }
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

