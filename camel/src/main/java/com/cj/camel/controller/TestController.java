package com.cj.camel.controller;

import com.cj.camel.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/testController")
    public Object testController() {
        return "success";
    }

    @RequestMapping("/RouterToController")
    public Object RouterToController(@RequestBody TestVO testVO) {
        logger.info("@@@RouterToController is running...,param:" + testVO);
        return "@@@ RouterToController success!";
    }
}
