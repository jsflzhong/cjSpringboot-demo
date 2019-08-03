package com.cj.listener.service.impl;

import com.cj.listener.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String testService1() {
        return "@@@test service success! 业务处理完成.";
    }
}
