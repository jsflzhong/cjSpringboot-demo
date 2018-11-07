package com.cj.anotation_spring.factory;

import com.cj.anotation_spring.Initializer.PaymentHandlerInitializer;
import com.cj.anotation_spring.annotation.handler.PaymentHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 代付工厂类.
 * 为了解耦和扩展性,抽象出来代付工厂.
 * 在"同一时刻只能有同一种代付渠道生效"的规则下,如果在以后扩展时,有"添加其他的代付渠道"的需求,
 * 则只需要简单的写一个该代付渠道的handler,并在类上使用@APaymentHandler注解把该handler注册进工厂即可.
 * 不需要改动本类.
 *
 * @author cj
 */
@Service
public class PaymentFactory {

    private volatile Map<String, PaymentHandler> handlerMap = new HashMap<>();

    @Autowired
    private PaymentHandlerInitializer paymentHandlerInitializer;

    /**
     * 初始化代付处理器map.
     *
     * @author cj
     */
    private void initHandlerMap() {
        handlerMap = paymentHandlerInitializer.getHandlerMap();
    }

    /**
     * 根据key获取对象的代付handler.
     *
     * @param key key
     * @return PaymentHandler
     * @author cj
     */
    public PaymentHandler getHandler(String key) {
        if (handlerMap.size() == 0) {
            synchronized (this) {
                if (handlerMap.size() == 0) {
                    initHandlerMap();
                }
            }
        }
        if (StringUtils.isEmpty(key)) {
            //可以考虑抛出自定义运行时异常.
            return null;
        }
        return handlerMap.get(key);
    }

}
