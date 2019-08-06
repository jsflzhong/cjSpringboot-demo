package com.cj.mybatis.service;

import java.util.List;

import com.cj.mybatis.domain.UserT;

public interface JunitSerivce {

    List<UserT> findAll();

    UserT findOne(long id);

    UserT save(UserT user);

    UserT update(UserT user);
}
