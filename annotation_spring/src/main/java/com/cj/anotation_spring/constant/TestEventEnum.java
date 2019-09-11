package com.cj.anotation_spring.constant;


public enum TestEventEnum {
    
    EVO_REJECTED("REJECTED"),
    EVO_CANCELED("CANCELED"),
    EVO_APPROVED("APPROVED");

    private final String name;

    TestEventEnum(String name) {
        this.name = name;
    }
}
