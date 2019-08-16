package com.cj.spring.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@Table(name = "user_t")
public class UserT {
    @Id
    @NotNull(message = "param: id can't be null")
    private Integer id;

    @Column(name = "user_name")
    @NotNull(message = "@@@如果值只有一个空格, 不会被该注解检查出来! 所以该注解一般用于基础类型!")
    @NotEmpty(message = "@@@如果值只有一个空格, 也会被该注解检查出来. 所以该注解一般用于String.")
    private String userName;

    //该注解决定了,
    // springMvc中requestBody接收json时,该字段的key的名字在入参里应该为pswd.
    // springMvc中responseBody返回json时,该字段的key的名字会替换为pswd.
    @JsonProperty("pswd")
    private String password;

    @JsonIgnore //springMvc中responseBody返回json时会生效,该字段不会生成在json中.
    private Integer age;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    @Override
    public String toString() {
        return "UserT{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
