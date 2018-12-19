package com.cj.druidMonitor.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cj
 * @description 通用Service接口
 * @date 2018/3/23
 */
@Service
public interface BaseService<T> {

    T selectByKey(Object key);

    List<T> selectByExample(Object example);

    int selectCount(T entity);

    int save(T entity);

    int delete(Object key);

    int updateAll(T entity);

    int updateNotNull(T entity);

    //其他方法...
}
