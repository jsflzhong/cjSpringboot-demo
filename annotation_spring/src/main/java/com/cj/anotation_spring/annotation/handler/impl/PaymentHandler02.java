package com.cj.anotation_spring.annotation.handler.impl;

import com.cj.anotation_spring.annotation.APaymentHandler;
import com.cj.anotation_spring.annotation.handler.PaymentHandler;
import com.cj.anotation_spring.constant.Identification;
import com.cj.anotation_spring.pojo.Entity;
import com.cj.anotation_spring.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 银生宝代付handler
 *
 * @author cj
 */
@Service
@APaymentHandler(Identification.IDENTIFICATION_02)
public class PaymentHandler02 implements PaymentHandler {

    private static Logger logger = LoggerFactory.getLogger(PaymentHandler02.class);

    @Autowired
    @Qualifier("BusinessServiceImpl02")
    private BusinessService businessService;

    /**
     * 处理代付逻辑
     *
     * @author cj
     */
    @Override
    public void handlePayment(List<? extends Entity> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            logger.info("@@@汇通-民生自动打款的数据集为空.");
            return;
        }
        businessService.execute(dataList);
    }
}
