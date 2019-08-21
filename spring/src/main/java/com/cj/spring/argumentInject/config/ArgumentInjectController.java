package com.cj.spring.argumentInject.config;

import com.cj.spring.common.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Slf4j
@RequestMapping("ArgumentInject")
public class ArgumentInjectController {

    /**
     * 测试:
     *  用配置类中注入的方式,为Controller层的User注入一个对象,而不是从前端传入.
     *  需要此功能的Controller只需要在形参上添加对应的entity即可.
     *  因为此参数并非要求调用方传入,所以用注解不让其在文档中显示.
     *
     * @param id 前端传入的参数, 无意义.
     * @param user 从配置类中注入的对象, 而不是从前端注入的.
     * @return Object
     */
    @GetMapping("/users")
    public Object test1(String id, @ApiIgnore User user) {
        log.info("@@@User已经被配置类中给注入了:" + user);
        return user;
    }
}
