package com.cj.camel;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@MapperScan(basePackages="com.cj.mybatis.dao")
//@EnableScheduling
public class Application {

    public static void main(String[] args) {
        final ApplicationContext context =
                new SpringApplication(Application.class).run(args);
        final CamelSpringBootApplicationController controller =
                context.getBean(CamelSpringBootApplicationController.class);
        controller.run();

    }
}
