package com.cj.anotation_spring.annotation.handler.impl;

import com.cj.anotation_spring.annotation.APaymentHandler;
import com.cj.anotation_spring.constant.Identification;
import com.cj.anotation_spring.pojo.Entity;
import com.cj.anotation_spring.service.BusinessService;
import com.cj.anotation_spring.annotation.handler.PaymentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 民生代付handler
 *
 * @author cj
 */
@Service
@APaymentHandler(Identification.IDENTIFICATION_01)
public class PaymentHandler01 implements PaymentHandler {

    private static Logger logger = LoggerFactory.getLogger(PaymentHandler01.class);

    @Autowired
    @Qualifier("BusinessServiceImpl01")
    private BusinessService businessService;

    /**
     * 处理代付逻辑
     *
     * @author cj
     */
    @Override
    public void handlePayment(List<? extends Entity> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            logger.info("@@@数据集为空.");
            return;
        }
        businessService.execute(dataList);
    }
}
