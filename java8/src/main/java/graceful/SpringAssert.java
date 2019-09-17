package graceful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * Spring自己出了一套Assert,会在内部抛出java1.8的运行时异常, 比较方便. (是Spring的, 挺好用的.)
 * 这里总结出一套常用的API, 争取调用的都是"抛出同样异常的"API, 方便上层抓取.
 *
 * 注意: 别忘记要抓对应的异常: IllegalArgumentException (下面用的API, 都值抓这一种异常即可!)
 *
 * 总结(使用时直接看这里):
 * 1.Object:                Assert.notNull()
 * 2.Collection or Map:     Assert.notEmpty();
 * 3.String:                Assert.hasText() //会检查出空格值.   或: Assert.hasLength() ;//不会检查出空格值.
 * 4.当表达式为假时,抛异常:  Assert.isTrue()   //封装类Boolean也可以被检查.
 *
 */
public class SpringAssert {

    public static void main(String[] args) {
        //test_object();
        //test_String2();
        //test_Collection();
        check_expression();
    }

    /**
     * 1.测试断言Object非空.
     * 测试结果:
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:@@@user is null!
     */
   private static void test_object() {
       try {
           Common.User user = null;
           Assert.notNull(user, "@@@user is null!");
           System.out.println("由于上行代码抛异常了,所以这里不会被执行到.");
       } catch (IllegalArgumentException e) {
           System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
       }
   }

    /**
     * 2.测试断言String非空.(不检查空格)
     * Assert.hasLength()
     * 注意,底层没有处理值为"空格"的字符串! 等于isEmpty,而不是isBlank, 如果需要检测空格, 要跳过这个,看下一个方法.
     *
     *测试结果:
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@a处理了
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@b处理了
     *  注意: 值为空格的字符串没被检查出来!
     *
     *
     */
    private static void test_String1() {
        String a = null;
        String b = "";
        String c = " "; //这个方法里, 空格检查不出来.

        try {
            Assert.hasLength(a,"@@@a处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.hasLength(b,"@@@b处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.hasLength(c,"@@@c处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }
    }

    /**
     * 3.测试断言String非空.(会检查空格)
     * Assert.hasText()
     * 注意,底层处理了值为"空格"的字符串! 等于isBlank(). 当需要也检查出值为空格的字符串时, 调用这套API.
     *
     *测试结果:
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@a处理了
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@b处理了
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@c处理了
     */
    private static void test_String2() {
        String a = null;
        String b = "";
        String c = " ";
        String d = " 1 ";

        try {
            Assert.hasText(a,"@@@a处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.hasText(b,"@@@b处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.hasText(c,"@@@c处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.hasText(d,"@@@d处理了...才怪, 我有值,不可能被此API抛异常.");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }
    }

    /**
     * 4.测试断言Collection或Map非空.(null或空元素都会被查出)
     * 对Collection检查空元素或null, 如果是就抛异常,也是与上面一样的异常.
     * 该API可以把List或Map或其他Collection一勺烩. 因为其底层是:CollectionUtils.isEmpty()
     *
     * 测试结果:
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@a处理了
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@b处理了
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@c处理了
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@d处理了
     */
    private static void test_Collection() {
        List a = null;
        List b = new ArrayList(); //初始化了,但是没元素

        Map c = null;
        Map d = new HashMap(); //初始化了,但是没元素

        List<String> ee = new ArrayList<>(); //放入元素的List就肯定不会被检查出来了.
        ee.add("1");


        try {
            Assert.notEmpty(a,"@@@a处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.notEmpty(b,"@@@b处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.notEmpty(c,"@@@c处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.notEmpty(d,"@@@d处理了");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.notEmpty(ee,"@@@ee处理了...才怪, 我有元素!");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }
    }

    /**
     * 5.检查传入的expression如果不为true,则抛异常.
     *
     * 注意, 是为假时,才会抛异常. 而不是为真时.
     * 注意, 没有这种API: 表达式为真时,就会抛异常. (没有!!! 只有假!)
     * 注意, 封装类Boolean也可以被检查.
     *
     * 测试结果:
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@a处理了,因为a是false,但是这里assert的是true,不符,所以抛异常了.
     *      @@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常, 模拟在这里处理...error:@@@c处理了,封装类,值为false
     */
    private static void check_expression() {
        boolean a = false;
        Boolean b = true; //封装类.
        Boolean c = false; //封装类.

        try {
            Assert.isTrue(a,"@@@a处理了,因为a是false,但是这里assert的是true,不符,所以抛异常了.");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.isTrue(b,"@@@b处理了,封装类,但值为true");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }

        try {
            Assert.isTrue(c,"@@@c处理了,封装类,值为false");
        } catch (IllegalArgumentException e) {
            System.out.println("@@@我是上层代码, 我抓住了底层用spring的Assert抛出的运行时异常,模拟在这里处理...error:" + e.getMessage());
        }
    }

}
