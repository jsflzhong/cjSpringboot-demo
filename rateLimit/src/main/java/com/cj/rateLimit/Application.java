package com.cj.rateLimit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.mapper.common.BaseMapper;

@SpringBootApplication
@MapperScan(basePackages="com.michael.springBoot.dao",markerInterface = BaseMapper.class)
//@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
