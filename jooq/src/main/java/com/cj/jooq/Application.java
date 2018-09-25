package com.cj.jooq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@MapperScan(basePackages="com.cj.shiro.dao",markerInterface = BaseMapper.class)
//@EnableScheduling
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

   /* protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
// 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(Application.class);
    }*/
}
