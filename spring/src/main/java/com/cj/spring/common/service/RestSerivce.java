package com.cj.spring.common.service;

import java.util.List;

import com.cj.spring.common.entity.UserT;


public interface RestSerivce {

    List<UserT> findAll();

    UserT findOne(long id);

    UserT save(UserT user);

    UserT update(UserT user);
}
