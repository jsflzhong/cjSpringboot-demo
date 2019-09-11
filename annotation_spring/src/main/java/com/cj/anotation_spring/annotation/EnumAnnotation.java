package com.cj.anotation_spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cj.anotation_spring.constant.TestEventEnum;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumAnnotation {
    TestEventEnum[] value();
}
