package com.cj.shiro.domain;

import javax.persistence.*;
import java.util.List;

@Table(name = "u_role")
public class Role {
    @Id
    private String id;

    /**
     * 角色名称,UI页面显示使用
     */
    private String name;

    /**
     * 角色类型.这是唯一标识.用于程序中判断用.
     */
    private String role;

    /**
     * 角色-权限 多对多
     * 在使用tk.mybatis建立实体时,如果数据库中没有此字段,需要瞬时注解,否则报错
     */
    @Transient
    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取角色名称,UI页面显示使用
     *
     * @return name - 角色名称,UI页面显示使用
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称,UI页面显示使用
     *
     * @param name 角色名称,UI页面显示使用
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取角色类型.这是唯一标识.用于程序中判断用.
     *
     * @return role - 角色类型.这是唯一标识.用于程序中判断用.
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置角色类型.这是唯一标识.用于程序中判断用.
     *
     * @param role 角色类型.这是唯一标识.用于程序中判断用.
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }
}