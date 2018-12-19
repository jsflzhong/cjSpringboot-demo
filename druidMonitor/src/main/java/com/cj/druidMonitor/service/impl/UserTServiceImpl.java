package com.cj.druidMonitor.service.impl;

import com.cj.druidMonitor.domain.UserT;
import com.cj.druidMonitor.service.UserTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jian.Cui on 2018/9/4.
 * Attention:
 * The service interface should also extends the BaseService interface.
 */
@Service
@Transactional
public class UserTServiceImpl extends BaseServiceImpl<UserT> implements UserTService {

    private final static Logger logger = LoggerFactory.getLogger(UserTServiceImpl.class);

    /**
     * 测试事务回滚
     *
     * @param userT userT
     * @return lines
     * @author cj
     */
    public int testTransaction(UserT userT) {
        int lines = save(userT);
        logger.info("@@@本次插入的行数是:[{}]行,下面准备触发异常来测试事务和回滚...",lines);
        if(1 == 1) {
            int i = 1/0;
        }
        return lines;
    }

}
