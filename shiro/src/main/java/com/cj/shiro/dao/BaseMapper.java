package com.cj.shiro.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author cj
 * @description 通用MAPPER接口,所有的dao接口需要继承此接口.
 * @date 2018/3/23
 */
public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T>{

}
