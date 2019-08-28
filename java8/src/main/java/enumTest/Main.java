package enumTest;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        test1();
    }

    static void test1() {
        //返回的类型是Enum
        Enum1 username = Enum1.USERNAME;
        System.out.println("username:" + username); //username:USERNAME

        //返回String, 值本质上与上面相同.
        String name = Enum1.USERNAME.name();
        System.out.println("name:" + name);//name:USERNAME
    }

    static void test2() {
        Enum2 evoMock = Enum2.EVO_MOCK;
        System.out.println(evoMock); //EVO_MOCK
        System.out.println(evoMock.name()); //EVO_MOCK
    }

    static void test3() {
        Enum3 evoMock = Enum3.EVO_MOCK;
        System.out.println(evoMock); //EVO_MOCK  还是key,与值无关
        System.out.println(evoMock.name()); //EVO_MOCK  还是key,与值无关

        //开始取值.
        Enum3[] values = Enum3.values();
        String first = values[0].getFirst();
        String second = values[0].getSecond();
        String third = values[0].getThird();
        System.out.println(first);//evo-mock
        System.out.println(second);//v2
        System.out.println(third);//v3

    }
}