package graceful;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.Assert;

import Common.UserWithLongId;

public class LogicalBoolean {

    public static void main(String[] args) {
        test1();
    }

    /**
     * test Boolean.logicalOr
     */
    public static void test1() {
        try {
            UserWithLongId user = new UserWithLongId();
            //user.setId(1L);
            //user.setName("1");
            //由于主键是Long对象,所以才能用Objects.nonNull, 如果是int,则会给默认值0.
            Assert.isTrue(Boolean.logicalOr(Objects.nonNull(user.getId()), StringUtils.isNotBlank(user.getName())),
                    "[insertThirdPartyLog]Id and Guid can't be all null in application.");
        } catch (Exception e) {
            System.out.println("@@@error:" + ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
    }
}
