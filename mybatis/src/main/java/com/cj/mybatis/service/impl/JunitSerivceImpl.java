package com.cj.mybatis.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cj.mybatis.controller.JunitController;
import com.cj.mybatis.domain.UserT;
import com.cj.mybatis.service.JunitSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JunitSerivceImpl implements JunitSerivce {

    private final static Logger logger = LoggerFactory.getLogger(JunitSerivceImpl.class);

    @Override
    public List<UserT> findAll() {
        logger.info("@@@findAll...");
        List<UserT> userList = new ArrayList<>();
        UserT userT = new UserT();
        userT.setId(1);
        userT.setAge(2);
        userT.setPassword("a");
        userT.setUserName("abc");

        UserT userT2 = new UserT();
        userT2.setId(2);
        userT2.setAge(3);
        userT2.setPassword("b");
        userT2.setUserName("abcd");

        userList.add(userT);
        userList.add(userT2);
        return userList;
    }

    @Override
    public UserT findOne(long id) {
        logger.info("@@@findOne...id:" + id);
        UserT userT = new UserT();
        userT.setId(1);
        userT.setAge(2);
        userT.setPassword("a");
        userT.setUserName("abc");
        return userT;
    }

    @Override
    public UserT save(UserT user) {
        logger.info("@@@save...");
        return user;
    }

    @Override
    public UserT update(UserT user) {
        logger.info("@@@update...");
        return user;
    }
}
