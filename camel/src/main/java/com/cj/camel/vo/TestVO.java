package com.cj.camel.vo;

public class TestVO {

    private String key1;
    private String key2;
    private String key3;

    public String getKey1() {
        return key1;
    }

    public TestVO setKey1(String key1) {
        this.key1 = key1;
        return this;
    }

    public String getKey2() {
        return key2;
    }

    public TestVO setKey2(String key2) {
        this.key2 = key2;
        return this;
    }

    public String getKey3() {
        return key3;
    }

    public TestVO setKey3(String key3) {
        this.key3 = key3;
        return this;
    }

    @Override
    public String toString() {
        return "TestVO{" +
                "key1='" + key1 + '\'' +
                ", key2='" + key2 + '\'' +
                ", key3='" + key3 + '\'' +
                '}';
    }
}
