package com.cj.shiro.dao;

import com.cj.shiro.domain.User;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 这里可以定义除了基类dao接口定义的方法之外的方法.
     * 其它的基础方法都继承自了基类dao接口.
     * 根据username查询该用户的pojo,角色pojo集合,权限pojo集合
     * @param username username
     * @return User
     * @author cj
     */
    User getUserRolePermissionByUsername(String username);
}