package com.cj.anotation_spring.annotation.handler;


import com.cj.anotation_spring.pojo.Entity;

import java.util.List;

/**
 * 代付handler上层接口.
 * 之所以要加这套Handler中间层,而不是直接把@APaymentHandler直接放到service层的类头上,
 * 是因为解耦,以及转换器模式.
 * 当然也可以简单的把@APaymentHandler注解直接用到Service层.
 *
 * @author cj
 */
public interface PaymentHandler {

    /**
     * 处理代付逻辑
     *
     * @author cj
     */
    void handlePayment(List<? extends Entity> dataList);
}
