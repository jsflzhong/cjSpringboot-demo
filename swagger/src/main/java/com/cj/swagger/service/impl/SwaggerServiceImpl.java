package com.cj.swagger.service.impl;

import java.util.Date;

import com.cj.swagger.entity.User;
import com.cj.swagger.service.SwaggerService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SwaggerServiceImpl implements SwaggerService {

    @Override
    public User getById(int id) {
        log.info("@@@getById(id) is running...");
        return new User().setId(id).setName("user1").setBirthday(new Date()).setAge(10);
    }

    @Override
    public User getByIdAndName(int id, String name) {
        log.info("@@@getByIdAndName is running...");
        return new User().setId(id).setName(name).setBirthday(new Date()).setAge(11);
    }

    @Override
    public User getById(User user) {
        log.info("@@@getById(user) is running...");
        return new User().setId(user.getId()).setName("user3").setBirthday(new Date()).setAge(10);
    }
}
