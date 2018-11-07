package com.cj.anotation_spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动打款handler注解.
 * 加上此注解的spring的bean,会作为一条新的代付渠道handler注册进代付渠道工厂中.
 * 从而可以被自动代付的入口定时任务(RealTimePaymentBatch)找到.
 * 注意,该注解在处理时依赖了IOC,所以使用本注解的类必须是个Spring的bean.
 *
 * @author cj
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface APaymentHandler {

    //对应的渠道标识. 例如如果是汇通民生,就对应常量类中配置的:HTMSZD
    public String value();
}
