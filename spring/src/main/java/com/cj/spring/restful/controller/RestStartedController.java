package com.cj.spring.restful.controller;

import java.util.List;

import com.cj.spring.common.entity.UserT;
import com.cj.spring.common.service.RestSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rest")
@Slf4j
public class RestStartedController {

    @Autowired
    private RestSerivce restService;

    /**
     * 查找所有用户
     *
     * @return 用户列表
     * @ResponseStatus. 注解底层还是通过设置  response.setStatus来实现.
     * 建议, 这种方式下使用千万不要加 reason, 就把@ResponseStatus  当做一个用来改变响应状态码的方式!
     * 该status可以在postman里看到.
     */
    @ApiOperation(value = "test get function.", notes = "test notes")
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<UserT> list() {
        return restService.findAll();
    }

    /**
     * 根据用户ID查找用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/user/{id}")
    public UserT get(@PathVariable("id") long id) {
        return restService.findOne(id);
    }

    /**
     * 创建一个用户
     *
     * @param user 用户信息
     * @return 用户信息
     * <p>
     * 该status一般用于新建数据时返回.
     */
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserT create(@RequestBody UserT user) {
        return restService.save(user);
    }

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return
     */
    @PutMapping("/user")
    public UserT update(@RequestBody UserT user) {
        return restService.update(user);
    }

}
