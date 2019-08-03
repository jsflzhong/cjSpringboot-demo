package interfaceNew.normalInterface;

public class SubClass2 implements SuperInterface {

    /**
     * 子类也可以覆盖父接口中的default方法
     */
    public void defaultFunction() {
        System.out.println("@@@This is the default function in subClass2.");
    }
}
