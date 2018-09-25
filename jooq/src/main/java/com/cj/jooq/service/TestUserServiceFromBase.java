package com.cj.jooq.service;

import com.cj.jooq.jooq.Tables;
import com.cj.jooq.jooq.tables.pojos.UUser;
import com.cj.jooq.service.base.AbstractJooqFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jian.Cui on 2018/9/25.
 * UUser service
 */
@Service
public class TestUserServiceFromBase extends AbstractJooqFactory<UUser> {

    @Override
    protected Pair<Class<UUser>, Table<? extends UpdatableRecord>> mapping() {
        return Pair.of(UUser.class, Tables.U_USER);
    }

    @Transactional
    public void testTransaction() {

    }

}