package com.cj.jooq.service;

import com.cj.jooq.jooq.tables.pojos.UUser;

import java.util.List;

/**
 * Created by Jian.Cui on 2018/9/25.
 * @deprecated This is just for test. I should use 'TestUserServiceFromBase' for development.
 */
@Deprecated
public interface TestJooqService{

        List<UUser> findAll();

        String save(UUser user);

}
