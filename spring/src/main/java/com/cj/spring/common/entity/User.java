package com.cj.spring.common.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class User {

    //int 默认初始化为0, 所以即使请求不带此值,也无法通过该注解查出来.
    @NotNull(message = "@User param: id can't be null")
    private int id;

    @NotNull(message = "@User param: name can't be null")
    private String name;

    private Date birthday;

    //该注解的值会显示在swagger的ui中的model中的对象字段说明里.
    @ApiModelProperty("用户年龄")
    private int age;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}
