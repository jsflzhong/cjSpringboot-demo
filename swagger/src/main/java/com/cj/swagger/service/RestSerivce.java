package com.cj.swagger.service;

import java.util.List;

import com.cj.swagger.entity.UserT;


public interface RestSerivce {

    List<UserT> findAll();

    UserT findOne(long id);

    UserT save(UserT user);

    UserT update(UserT user);
}
