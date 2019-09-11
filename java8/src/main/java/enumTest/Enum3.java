package enumTest;

public enum Enum3 {
    EVO_MOCK("evo-mock","v2","v3"),
    EVO_LOGIN("login","v4","v5"),
    EVO_LANDING("landing","v6","v7");

    String first;
    String second;
    String third;

    /**
     * 构造函数的形参的数量,要与字段的值得数量相同.
     * @param name
     */
    Enum3(String name,String v1,String v2) {
        this.first = name;
        this.second = v1;
        this.third = v2;
    }

    public String getFirst() {
        return first;
    }

    public Enum3 setFirst(String first) {
        this.first = first;
        return this;
    }

    public String getSecond() {
        return second;
    }

    public Enum3 setSecond(String second) {
        this.second = second;
        return this;
    }

    public String getThird() {
        return third;
    }

    public Enum3 setThird(String third) {
        this.third = third;
        return this;
    }
}
