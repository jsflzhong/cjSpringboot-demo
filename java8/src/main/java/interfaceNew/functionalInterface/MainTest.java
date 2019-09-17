package interfaceNew.functionalInterface;


import Common.User;

public class MainTest {

    public static void main(String[] args) {
        try {
            test6();
        } catch (IllegalArgumentException e) {
            System.out.println("error:@@@111111111");
        }
    }

    //test result:111
    public static void test1() {
        boolean condition = true;
        Executor.doIfTrue(condition, () -> System.out.println("111"));

        condition = false;
        Executor.doIfTrue(condition, () -> System.out.println("222"));

    }

    //222
    public static void test2() {
        boolean condition = false;
        Executor.doAfterCheck(condition,
                () -> System.out.println("111"),
                () -> System.out.println("222"));
    }

    /**
     * 注意:
     *  lambda中抛出的异常,在上层函数中是可以用try捕获的(注意异常的名字一定要一致即可).
     *
     */
    public static void test3() {
        boolean condition = true;
        Executor.doIfTrue(condition, () -> {
            throw new IllegalArgumentException("111");
        });
        System.out.println("222"); //Success skip this line.
    }

    /**
     * User{id=1, name='newName', birthday=null}
     */
    public static void test4() {
        boolean condition = true;

        Executor<User> userExecutor = new Executor<>();
        User user = new User().setId(1);

        User newUser = userExecutor.produceIfTrue(condition, () -> user.setName("newName"));

        System.out.println(newUser);
    }

    /**
     * id:1
     */
    public static void test5() {
        boolean condition = true;

        Executor<Integer> userExecutor = new Executor<>();
        User user = new User().setId(1);

        int id = userExecutor.produceIfTrue(condition, () -> user.getId());

        System.out.println("id:" + id);
    }

    /**
     * id:2
     */
    public static void test6() {
        boolean condition = false;

        Executor<Integer> userExecutor = new Executor<>();
        User user = new User().setId(1);

        int id = userExecutor.produceAfterCheck(condition, () -> user.getId(), () -> 2);

        System.out.println("id:" + id);
    }
}
