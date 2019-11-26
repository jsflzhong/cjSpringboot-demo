package com.cj.mybatis.service;

import java.util.List;

import com.cj.common.entity.ResponseBean;
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

    //test中测试用的方法
    ResponseBean<List<UserT>> getByUsernameForUnitTest(String userName);

    ResponseBean<List<UserT>> testTransaction_inSameClass(UserT userT);

    ResponseBean<List<UserT>> testTransaction_inDiffClass(UserT userT);
}
