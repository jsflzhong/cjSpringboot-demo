package com.cj.anotation_spring.service.Impl;

import com.cj.anotation_spring.pojo.Entity;
import com.cj.anotation_spring.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jian.Cui on 2018/11/7.
 *
 * @author Jian.Cui
 */
@Service("BusinessServiceImpl01")
public class BusinessServiceImpl01 implements BusinessService {

    private static Logger logger = LoggerFactory.getLogger(BusinessServiceImpl01.class);

    @Override
    public void execute(List<? extends Entity> dataList) {
        logger.info("@@@BusinessServiceImpl01 is running...");
    }
}
