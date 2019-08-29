package com.cj.spring.restful.util;

import org.springframework.core.NamedThreadLocal;

public class FieldFilterUtil {

    private FieldFilterUtil() {}

    private static NamedThreadLocal<Class<?>> threadLocal = new NamedThreadLocal<>("field-filter-class-thread-local");

    public static Class<?> get() {
        return threadLocal.get();
    }

    public static void apply(Class<?> clazz) {
        threadLocal.set(clazz);
    }

    public static void remove() {
        threadLocal.remove();
    }

}
