package com.cj.cache.service.impl;

import com.cj.cache.pojo.DataValue;
import com.cj.cache.service.DataValueService;
import com.cj.cache.vo.BusinessResponse;
import org.springframework.stereotype.Service;

/**
 * Created by jsflz on 2018/8/22.
 */
@Service
public class DataValueServiceImpl implements DataValueService {

    @Override
    public DataValue getDataValueById_test(Integer id) {
        return new DataValue("testFunction");
    }

    @Override
    public String getStringByKey_test(String key) {
        return "test1";
    }

    @Override
    public DataValue getStringByKey_test2(String key) {
        return new DataValue("Test success!");
    }

    @Override
    public BusinessResponse insert(DataValue dataValue) {
        //insert into db...
        int lines = 1;

        return new BusinessResponse()
                .setResponseCode(lines>0?0:1)
                .setResponseMsg(lines>0?BusinessResponse.ResponseCode.SUCCESS.name():"failed : cannot insert data ");
    }
}
