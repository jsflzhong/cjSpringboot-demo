package com.cj.shiro.domain;

import javax.persistence.*;

@Table(name = "u_permission")
public class Permission {
    @Id
    private String id;

    /**
     * url地址
     */
    private String url;

    /**
     * url描述
     */
    private String name;

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;

    /**
     * 父编号
     */
    @Column(name = "parentId")
    private String parentid;

    /**
     * 父编号列表列表
     */
    @Column(name = "parentIds")
    private String parentids;

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
     * 获取url地址
     *
     * @return url - url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url地址
     *
     * @param url url地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取url描述
     *
     * @return name - url描述
     */
    public String getName() {
        return name;
    }

    /**
     * 设置url描述
     *
     * @param name url描述
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     *
     * @return permission - 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     *
     * @param permission 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * 获取父编号
     *
     * @return parentId - 父编号
     */
    public String getParentid() {
        return parentid;
    }

    /**
     * 设置父编号
     *
     * @param parentid 父编号
     */
    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    /**
     * 获取父编号列表列表
     *
     * @return parentIds - 父编号列表列表
     */
    public String getParentids() {
        return parentids;
    }

    /**
     * 设置父编号列表列表
     *
     * @param parentids 父编号列表列表
     */
    public void setParentids(String parentids) {
        this.parentids = parentids == null ? null : parentids.trim();
    }
}