package graceful;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class ForString {

    public static void main(String[] args) {
        test_notNullAndEquals("aaa");
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
    }
}
