package com.cj.druidMonitor;

import com.cj.druidMonitor.dao.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//这个路径要写对,复制过来时特别容易忘记改.否则服务启动失败,dao层DI失败.
@MapperScan(basePackages="com.cj.druidMonitor.dao",markerInterface = BaseMapper.class)
//@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
