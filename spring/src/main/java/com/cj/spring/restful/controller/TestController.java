package com.cj.spring.restful.controller;

import com.cj.common.entity.ResponseBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/endPoint")
    public Object testEndpoint(String id) {
      log.info("@@@endPoint-01 getting the request, id:{}",id);
      return new ResponseBean<>(1,"@@@This is endPoint-01");
    }
}
