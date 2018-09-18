package com.cj.mybatis.service;

import com.cj.mybatis.domain.UserT;

/**
 * Created by Jian.Cui on 2018/9/4.
 */
public interface UserTService extends BaseService<UserT> {

    /**
     * 测试事务回滚
     *
     * @param userT userT
     * @return lines
     * @author cj
     */
    int testTransaction(UserT userT);
}
