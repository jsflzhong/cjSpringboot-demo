package com.cj.anotation_spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 第三期解决方案测试.
 *
 * @author cj
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Period3 {

    //对应的渠道标识. 例如如果是汇通民生,就对应常量类中配置的:HTMSZD
    public String value();
}
