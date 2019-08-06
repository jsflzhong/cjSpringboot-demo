package com.cj.mybatis.controller;

import java.util.List;

import com.cj.mybatis.domain.UserT;
import com.cj.mybatis.service.JunitSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JunitController {

    private final static Logger logger = LoggerFactory.getLogger(JunitController.class);

    @Autowired
    private JunitSerivce junitSerivce;

    /**
     * 查找所有用户
     * @return 用户列表
     */
    @GetMapping("/user")
    public List<UserT> list(){
        return junitSerivce.findAll();
    }

    /**
     * 根据用户ID查找用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/user/{id}")
    public UserT get(@PathVariable("id") long id){
        return junitSerivce.findOne(id);
    }

    /**
     * 创建一个用户
     * @param user 用户信息
     * @return 用户信息
     */
    @PostMapping("/user")
    public UserT create(@RequestBody UserT user){
        return junitSerivce.save(user);
    }

    /**
     * 更新用户
     * @param user 用户信息
     * @return
     */
    @PutMapping("/user")
    public UserT update(@RequestBody UserT user){
        return junitSerivce.update(user);
    }
}
