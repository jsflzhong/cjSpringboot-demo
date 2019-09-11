package com.cj.spring.argumentInject.config;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfiguration implements WebMvcConfigurer {

    protected static final Logger logger = LogManager.getLogger();

    @Autowired
    private ControllerArgumentResolver controllerArgumentResolver;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(xxInterceptor).addPathPatterns(getStepPathAndTypeCheckPatterns());
        //registry.addInterceptor(xxInterceptor).addPathPatterns("/**");
    }

    private List<String> getStepPathAndTypeCheckPatterns() {
        return ImmutableList.of("/v1/applications/**/photos/**", "/v1/applications/**/videos/**",
                "/v1/applications/**/verification-codes/**", "/v1/applications/**/checks/**",
                "/v1/applications/**/step-verifications/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(controllerArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverterFactory(new EvoEnumConverterFactory());
    }

}
