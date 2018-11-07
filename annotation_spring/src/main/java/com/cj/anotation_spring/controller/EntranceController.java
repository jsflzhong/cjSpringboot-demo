package com.cj.anotation_spring.controller;

import com.cj.anotation_spring.annotation.handler.PaymentHandler;
import com.cj.anotation_spring.constant.Identification;
import com.cj.anotation_spring.factory.PaymentFactory;
import com.cj.anotation_spring.pojo.Entity;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Created by Jian.Cui on 2018/11/7.
 * 入口Controller.
 * 根据从db或配置中拿到的标识,在运行时动态获取对应的处理器来处理业务
 *
 * @author Jian.Cui
 */
@Controller
public class EntranceController {

    @Autowired
    private PaymentFactory paymentFactory;

    @RequestMapping("/entrance")
    public Object entrance() {
        //从db,或配置,或接口中拿到标识
        String identification = getIdentification();

        //模拟需要处理的数据.
        ArrayList<Entity> dataList = getData();

        //动态调用对应的处理器来处理数据业务
        PaymentHandler handler = paymentFactory.getHandler(identification);
        handler.handlePayment(dataList);

        return "1";
    }

    private ArrayList<Entity> getData() {
        //模拟需要处理的数据.一般是从DB中先查出来的.
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        return Lists.newArrayList(entity1, entity2);
    }

    private String getIdentification() {
        //模拟从db,或配置,或接口中拿到标识
        return Identification.IDENTIFICATION_02;
    }

}
