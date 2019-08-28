package interfaceNew.normalInterface;

public interface SuperInterface1 {

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
