package interfaceNew.normalInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

/**
 * Used to test the autowired in the interface.
 * Check if it can be get in the initial classes.
 */
public interface SuperInterface2 {

    @Autowired
    @Qualifier("subClass2")
    @Lazy
    //SuperInterface1 subClass2;  can't be dones

    /**
     * java8以后,接口中可以有静态方法了.
     * 注意:实现接口的类或者子接口不会继承接口中的静态方法!
     */
    static void staticFunction() {
        System.out.println("@@@This is the static function in super interface.");
    }

    default void defaultFunction() {
        System.out.println("@@@This is the default function in super interface.");
    }

}
