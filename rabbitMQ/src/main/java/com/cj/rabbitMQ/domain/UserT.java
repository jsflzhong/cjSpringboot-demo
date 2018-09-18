package com.cj.rabbitMQ.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

public class UserT {
    private Integer id;

    private String userName;

    private String password;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public UserT setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserT setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserT setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserT setAge(Integer age) {
        this.age = age;
        return this;
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