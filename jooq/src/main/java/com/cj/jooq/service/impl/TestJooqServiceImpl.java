package com.cj.jooq.service.impl;

import com.cj.jooq.jooq.tables.pojos.UUser;
import com.cj.jooq.jooq.tables.records.UUserRecord;
import com.cj.jooq.service.TestJooqService;
import com.google.common.collect.Lists;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cj.jooq.jooq.tables.UUser.U_USER;

/**
 * Created by Jian.Cui on 2018/9/25.
 * @deprecated This is just for test. I should use 'TestUserServiceFromBase' for development.
 */
@Service
@Deprecated
public class TestJooqServiceImpl implements TestJooqService {

    @Autowired
    private DSLContext create;


    @Override
    public List<UUser> findAll() {
        //Return "record".
        List<UUserRecord> userRecordList = create
                .select()
                .from(U_USER) //static constant
                .fetchInto(UUserRecord.class); //record

        if(userRecordList == null || userRecordList.isEmpty()) {
            return Lists.newArrayList(new UUser());
        }

        ArrayList<UUser> userList = new ArrayList<>();
        for (UUserRecord userRecord : userRecordList) {
            UUser uUser = new UUser(userRecord.getId(),
                    userRecord.getUsername(),
                    userRecord.getRealname(),
                    userRecord.getEmail(),
                    userRecord.getPassword(),
                    userRecord.getLastLoginTime(),
                    userRecord.getStatus(),
                    userRecord.getSalt(),
                    userRecord.getAge(),
                    userRecord.getCreateTime(),
                    userRecord.getCreateBy(),
                    userRecord.getUpdateTime(),
                    userRecord.getUpdateBy());
            userList.add(uUser);
        }
        return userList;
    }

    @Override
    public String save(UUser user) {
        create
                .insertInto(U_USER,U_USER.ID,U_USER.USERNAME,U_USER.REALNAME,U_USER.EMAIL,U_USER.PASSWORD)
                .values(user.getId(),user.getUsername(),user.getRealname(),user.getEmail(),user.getPassword())
                .execute();
        return "success";
    }
}
