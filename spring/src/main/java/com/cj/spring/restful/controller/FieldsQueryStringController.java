package com.cj.spring.restful.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.cj.spring.common.entity.Application;
import com.cj.spring.common.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fields")
@Slf4j
public class FieldsQueryStringController {

    /**
     * Use Spring's Build in to do it.
     * 测试目标:
     *  前端指定"要返回的字段名", 后端只返回这些字段.(与查询无关,该查什么字段还差什么字段, 甚至全字段都查出来)
     * 技术实现:
     *  添加后置过滤器,对后端返回的全对象进行过滤,拿出前端要求的字段组成JSON返回.
     * 概念:
     *  该功能并不是之前想的: 前端要查哪些字段, 后端在对DB时就只查这些字段并返回.
     *  而是:前端要哪些字段, 后端就返回哪些字段, 与怎么对DB查无关(可后续自己实现)
     *  过滤器加的是后置的.
     * 配置:
     *  见confi目录下的两个配置文件, 和一个util.
     *  AppWebMvcConfiguration该配置用来清除ThreadLocal里面的值.收尾工作.
     * 注意:
     *  Entity的需要该功能的字段上,需要加上注解: Jackson自带注解@JsonFilter
     *  见Entity: Application
     *
     * Attention:
     *  1.The case of fields in entity will be change to SNAKE_CASE automatically then return to FE, like: pruchaseTime-->pruchase_time (Based on the config in JacksonConfig)
     *  2.Based on 2, the fields's value in the param of controller(request) should be SNAKE_CASE too, or else can't pass the check: needSerialize(), means can't response this field to FE.
     *       like:http://localhost:8013/fields/test1?fields=id,name,pswd,pruchaseTime (NO, can't get the field:pruchaseTime)
     *       like:http://localhost:8013/fields/test1?fields=id,name,pswd,pruchase_time (YES)
     *
     *  test
     *  url(without included Object):http://localhost:8013/fields/test1?fields=id,name,pswd,pruchase_time  (SNAKE_CASE!)
     *  return:{"id":1,"name":"name1","pswd":"password123","pruchase_time":"2019-08-29T06:56:38.561+0000"}
     *
     *  url(with included Object "user"):http://localhost:8013/fields/test1?fields=id,name,pswd,pruchase_time,user
     *  return:{"id":1,"name":"name1","pswd":"password123","pruchase_time":"2019-08-29T07:21:30.146+0000","user":{"id":2,"name":"testUser","birthday":"2019-08-29T07:21:30.146+0000","age":3}}
     *
     *
     * @param fields fields
     * @return obj
     */
    @GetMapping("/test1")
    public Object test1(@RequestParam(name="fields", required = false) Set<String> fields) {
        log.info("@@@FE needs fields:" + fields);
        return new Application()
                .setId(1L).setName("name1").setPrice(new BigDecimal("100"))
                .setPruchaseTime(new Date()).setPswd("password123")
                .setUser(new User().setId(2).setBirthday(new Date()).setAge(3).setName("testUser"));
    }
}
