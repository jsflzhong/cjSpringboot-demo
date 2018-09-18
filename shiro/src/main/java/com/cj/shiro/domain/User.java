package com.cj.shiro.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "u_user")
public class User {
    @Id
    private String id;

    /**
     * 用户登录名
     */
    @Column(name = "userName")
    private String username;

    /**
     * 用户真实姓名
     */
    private String realname;

    /**
     * 邮箱|登录帐号
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 1:有效，0:禁止登录
     */
    private Long status;

    /**
     * 加密密码的盐
     */
    private String salt;

    private Integer age;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人id
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 修改人id
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 一个用户具有多个角色
     * 在使用tk.mybatis建立实体时,如果数据库中没有此字段,需要瞬时注解,否则报错.
     * mybatis的关联查询方式: 在mapper.xml中必须映射,才能保存查询到的数据.
     */
    @Transient
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
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
     * 获取用户登录名
     *
     * @return userName - 用户登录名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户登录名
     *
     * @param username 用户登录名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取用户真实姓名
     *
     * @return realname - 用户真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置用户真实姓名
     *
     * @param realname 用户真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    /**
     * 获取邮箱|登录帐号
     *
     * @return email - 邮箱|登录帐号
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱|登录帐号
     *
     * @param email 邮箱|登录帐号
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取最后登录时间
     *
     * @return last_login_time - 最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取1:有效，0:禁止登录
     *
     * @return status - 1:有效，0:禁止登录
     */
    public Long getStatus() {
        return status;
    }

    /**
     * 设置1:有效，0:禁止登录
     *
     * @param status 1:有效，0:禁止登录
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * 获取加密密码的盐
     *
     * @return salt - 加密密码的盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置加密密码的盐
     *
     * @param salt 加密密码的盐
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人id
     *
     * @return create_by - 创建人id
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人id
     *
     * @param createBy 创建人id
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取修改人id
     *
     * @return update_by - 修改人id
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置修改人id
     *
     * @param updateBy 修改人id
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}