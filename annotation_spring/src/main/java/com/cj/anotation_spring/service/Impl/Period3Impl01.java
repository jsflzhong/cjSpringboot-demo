package com.cj.anotation_spring.service.Impl;

import java.util.List;

import com.cj.anotation_spring.annotation.Period3;
import com.cj.anotation_spring.constant.Identification;
import com.cj.anotation_spring.pojo.Entity;
import com.cj.anotation_spring.service.Period3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Jian.Cui on 2018/11/7.
 *
 * @author Jian.Cui
 */
@Service
@Period3(Identification.PERIOD_03_a)
public class Period3Impl01 implements Period3Service {

    private static Logger logger = LoggerFactory.getLogger(Period3Impl01.class);

    @Override
    public void process() {
        logger.info("@@@Period3Impl01 is running...");
    }
}
