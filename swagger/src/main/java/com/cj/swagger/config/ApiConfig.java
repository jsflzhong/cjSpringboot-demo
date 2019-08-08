package com.cj.swagger.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableSwagger2Doc
@PropertySource(value = {
        "classpath:conf/api-cfg.properties"
})
public class ApiConfig {

    /*@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cj.mybatis.controller")) //配置只在swagger ui上显示这个包下的所有接口.
                .paths(PathSelectors.any())
                .build();
    }*/

}
