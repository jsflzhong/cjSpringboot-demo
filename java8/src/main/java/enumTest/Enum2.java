package enumTest;

public enum Enum2 {
    EVO_MOCK("evo-mock"),
    EVO_LOGIN("login"),
    EVO_LANDING("landing");

    /**
     * 如果字段带值,则必须要一个带形参的构造函数,否则报错.
     * @param name
     */
    Enum2(String name) {

    }
}
