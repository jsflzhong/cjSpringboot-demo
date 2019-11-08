package com.cj.concurrent.product.controller;

import com.cj.concurrent.product.service.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/threadPool")
public class ThreadPoolController {

    @Autowired
    private ThreadPoolService threadPoolService;

    @GetMapping("/test1")
    public Object test1() {
        threadPoolService.useThreadPool1();
        return "1";
    }

    @GetMapping("/test2")
    public Object test2() {
        threadPoolService.useThreadPool2();
        return "1";
    }
    @GetMapping("/test3")
    public Object test3() {
        threadPoolService.useThreadPool3();
        return "1";
    }

    @GetMapping("/test4")
    public Object test4() {
        threadPoolService.useThreadPool4();
        return "1";
    }

    @GetMapping("/test5")
    public Object test5() {
        threadPoolService.useThreadPool5();
        return "1";
    }
}
