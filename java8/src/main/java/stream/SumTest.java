package stream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Common.User;

public class SumTest {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 用两种方式求list中的元素对象中的某个字段的和.
     */
    public static void test1() {
        User user1 = new User().setId(1);
        User user2 = new User().setId(2);
        User user3 = new User().setId(3);

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        //方式一 亲测OK
        Integer totalId1 = users.stream().collect(Collectors.summingInt(user -> user.getId()));
        System.out.println("@@@totalid1:" + totalId1);

        //方式二 亲测OK
        Integer totalId2 = users.stream().mapToInt(user -> user.getId()).sum();
        System.out.println("@@@totalid2:" + totalId2);


        /**如果list里是空元素 的情况*/
        ArrayList<User> users2 = new ArrayList<>();
        //方式一 亲测OK 结果是0, 不会报空指针
        Integer totalId3 = users2.stream().collect(Collectors.summingInt(user -> user.getId()));
        System.out.println("@@@totalid3:" + totalId3);

        //方式二 亲测OK 结果是0, 不会报空指针
        Integer totalId4 = users2.stream().mapToInt(user -> user.getId()).sum();
        System.out.println("@@@totalid4:" + totalId4);


        /**如果list里是空元素,且需要在 BigDecimal和Integer之间转换 的情况*/
        ArrayList<User> users3 = new ArrayList<>();
        //方式一 亲测OK 结果是0, 不会报空指针
        Integer totalId5 = users3.stream().collect(Collectors.summingInt(user -> user.getPrice().intValue()));
        System.out.println("@@@totalid5:" + totalId5);

        //方式二 亲测OK 结果是0, 不会报空指针
        Integer totalId6 = users3.stream().mapToInt(user -> user.getPrice().intValue()).sum();
        System.out.println("@@@totalid6:" + totalId6);

        //亲测不会报空指针,结果是0.
        BigDecimal bigDecimal = new BigDecimal(totalId5 + totalId6);
        System.out.println("@@@final:" + bigDecimal);
    }
}
