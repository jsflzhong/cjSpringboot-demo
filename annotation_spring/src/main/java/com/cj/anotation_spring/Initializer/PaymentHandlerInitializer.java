package com.cj.anotation_spring.Initializer;

import com.cj.anotation_spring.annotation.APaymentHandler;
import com.cj.anotation_spring.annotation.handler.PaymentHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * PaymentHandler注解处理器
 * 在服务启动时,动态获取IOC中所有的"带有我自定义注解的bean",并缓存起来, PaymentFactory会使用到这个缓存.
 * 可以合二为一,去掉其中之一.
 *
 * @author cj
 */
@Component
public class PaymentHandlerInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private Map<String, PaymentHandler> handlerMap = new HashMap<>();

    /**
     * 根据自定义注解的bean,初始化代付handler的缓存.
     *
     * @param event ContextRefreshedEvent
     * @author cj
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> beansWithAnnotation = event.getApplicationContext()
                    .getBeansWithAnnotation(APaymentHandler.class);
            for (Object beanClass : beansWithAnnotation.values()) {
                APaymentHandler annotation = beanClass.getClass().getAnnotation(APaymentHandler.class);
                String value = annotation.value();
                if (StringUtils.isNotEmpty(value) && beanClass instanceof PaymentHandler) {
                    PaymentHandler bean = (PaymentHandler) beanClass;
                    handlerMap.put(value, bean);
                }
            }
        }
    }

    public Map<String, PaymentHandler> getHandlerMap() {
        return handlerMap;
    }
}
