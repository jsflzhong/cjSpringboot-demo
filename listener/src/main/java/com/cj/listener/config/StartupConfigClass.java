package com.cj.listener.config;

import javax.servlet.http.HttpSessionListener;

import com.cj.listener.listener.shutdownListener.ShutdownListener1;
import com.cj.listener.listener.startupListener.interceptor.PreInterceptor1;
import com.cj.listener.listener.startupListener.listener.PreListener1;
import com.cj.listener.listener.startupListener.listener.PreListener2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 请求到达Controller之前的监听器的配置文件
 */
@Configuration
public class StartupConfigClass implements WebMvcConfigurer {

    @Autowired
    private PreInterceptor1 preInterceptor1;
    @Autowired
    private PreListener1 preListener1;
    @Autowired
    private PreListener2 preListener2;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(preInterceptor1);
    }

}
