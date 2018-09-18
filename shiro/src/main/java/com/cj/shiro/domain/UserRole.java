package com.cj.shiro.domain;

import javax.persistence.*;

@Table(name = "u_user_role")
public class UserRole {
    @Id
    private String id;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 角色ID
     */
    private String rid;

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
     * 获取用户ID
     *
     * @return uid - 用户ID
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置用户ID
     *
     * @param uid 用户ID
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取角色ID
     *
     * @return rid - 角色ID
     */
    public String getRid() {
        return rid;
    }

    /**
     * 设置角色ID
     *
     * @param rid 角色ID
     */
    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }
}