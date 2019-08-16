package com.cj.anotation_spring.config;

import java.util.HashMap;
import java.util.Map;

import com.cj.anotation_spring.annotation.Period3;
import com.cj.anotation_spring.service.Impl.Period3Impl01;
import com.cj.anotation_spring.service.Period3Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanMapConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Bean(name = "period3ServiceMap")
    public Map<String, Period3Service> period3ServiceMap() {
        HashMap<String, Period3Service> beansMap = new HashMap<>();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Period3.class);
        beansWithAnnotation.values().forEach(beanClass -> {
            String value = beanClass.getClass().getAnnotation(Period3.class).value();
            if (StringUtils.isNotEmpty(value) && beanClass instanceof Period3Service) {
                beansMap.put(value, (Period3Service)beanClass);
            }
        });
        return beansMap;
    }
}
