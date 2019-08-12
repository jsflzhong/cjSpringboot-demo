package com.cj.mybatis.service.impl;

import java.util.Arrays;
import java.util.List;

import com.cj.common.entity.ResponseBean;
import com.cj.mybatis.domain.UserT;
import com.cj.mybatis.service.UserTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jian.Cui on 2018/9/4.
 * Attention:
 * The service interface should also extends the BaseService interface.
 */
@Service
public class UserTServiceImpl extends BaseServiceImpl<UserT> implements UserTService {

    private final static Logger logger = LoggerFactory.getLogger(UserTServiceImpl.class);

    /**
     * 测试事务回滚
     *
     * @param userT userT
     * @return lines
     * @author cj
     */
    @Transactional
    public int testTransaction(UserT userT) {
        int lines = save(userT);
        logger.info("@@@本次插入的行数是:[{}]行,下面准备触发异常来测试事务和回滚...",lines);
        if(1 == 1) {
            int i = 1/0;
        }
        return lines;
    }

    //test中测试用的方法
    @Override
    public ResponseBean<List<UserT>> getByUsernameForUnitTest(String userName) {
        System.out.println("@@@getByUsernameForUnitTest is really running...");

        UserT userT = new UserT();
        userT.setId(1);
        userT.setAge(2);
        userT.setPassword(userName);
        userT.setUserName("abc");

        return ResponseBean.success(Arrays.asList(userT));
    }

    /**
     * 测试在同一个bean中, 互相调用的情况下, 事务是如何失效的.
     * 注意, @Transactional注解只能用在可以被重写的方法上,即不能在private方法上!!!
     *
     * test case:
     *  在testTransaction_inSameClass()中insert,然后调用同类中的testTransaction_inSameClass_update(),
     *  后者会在update db之后抛异常, 观察insert的数据是否回滚了.
     *
     * @param userT userT
     * @return ResponseBean
     */
    @Override
    @Transactional
    public ResponseBean<List<UserT>> testTransaction_inSameClass(UserT userT) {
        save(userT);

        //testTransaction_inSameClass_update(userT);

        testTransaction_inSameClass_update_inProtected(userT);

        return ResponseBean.successNoData();
    }

    /**
     * 被testTransaction_inSameClass可以回滚
     * @param userT
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void testTransaction_inSameClass_update(UserT userT) {
        userT.setPassword("newPswd");
        updateAll(userT);

        if(1 == 1) {
            int i = 1/0;
        }
    }

    /**
     * 被testTransaction_inSameClass可以回滚
     * @param userT
     */
    @Transactional(propagation = Propagation.MANDATORY)
    protected void testTransaction_inSameClass_update_inProtected(UserT userT) {
        userT.setPassword("newPswd2");
        updateAll(userT);

        if(1 == 1) {
            int i = 1/0;
        }
    }

}
