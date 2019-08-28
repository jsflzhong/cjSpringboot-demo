package enumTest;

public enum Enum4_includeEnum3 {
    USERNAME(Enum3.EVO_MOCK);

    /**
     * 如果字段带值,则必须要一个带形参的构造函数,否则报错.
     */
    Enum4_includeEnum3(Enum3 testField) {
        this.testField = testField;
    }

    private final Enum3 testField;

    public Enum3 testField() {
        return testField;
    }
}
