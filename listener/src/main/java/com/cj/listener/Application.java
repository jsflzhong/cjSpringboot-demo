package com.cj.listener;

import com.cj.listener.listener.startupListener.listener.PreListener1;
import com.cj.listener.listener.startupListener.listener.PreListener2;
import com.cj.listener.listener.startupListener.listener.PreListener3;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.mapper.common.BaseMapper;

@SpringBootApplication
@MapperScan(basePackages="com.michael.springBoot.dao",markerInterface = BaseMapper.class)
//@EnableScheduling
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		SpringApplication app=new SpringApplication(Application.class);
		app.addListeners(new PreListener1());
		app.addListeners(new PreListener2());
		app.addListeners(new PreListener3());
		app.run(args);
	}
}
