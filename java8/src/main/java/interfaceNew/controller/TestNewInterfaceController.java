package interfaceNew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import interfaceNew.normalInterface.SuperInterface2;

@RestController
public class TestNewInterfaceController {

    @Autowired
    private SuperInterface2 superInterface3Impl;

    @GetMapping("/test1")
    public Object test1() {
        superInterface3Impl.defaultFunction();
        return 1;
    }
}
