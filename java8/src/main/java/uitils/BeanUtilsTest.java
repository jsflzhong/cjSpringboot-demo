package uitils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import Common.User;
import Common.UserWithStatus;

public class BeanUtilsTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        UserWithStatus userWithStatus = new UserWithStatus().setId(1).setName("n1").setStatus("222");
        User user = new User();
        UserWithStatus userWithStatus2 = new UserWithStatus().setId(1).setName("n1");
        try {
            BeanUtils.copyProperties(user,userWithStatus);
            BeanUtils.copyProperties(userWithStatus2,userWithStatus);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("111");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("222");
        }
        System.out.println(user);
        System.out.println(userWithStatus2);
    }
}
