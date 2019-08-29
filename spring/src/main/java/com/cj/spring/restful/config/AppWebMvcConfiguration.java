package com.cj.spring.restful.config;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cj.spring.restful.util.FieldFilterUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppWebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper mapper) {
        return new EnhancedMappingJackson2HttpMessageConverter(mapper);
    }

    class EnhancedMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        public EnhancedMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
            super(objectMapper);
        }

        /**
         * Execute sequence:
         *  1.writeInternal() 's first line!
         *  2.serializeAsField() in JacksonConfig.class in loop! (set value into ThreadLocal)
         *  3.back to here in writeInternal() 's second line!  (remove value into ThreadLocal)
         *
         * @param object
         * @param type
         * @param outputMessage
         * @throws IOException
         */
        @Override
        protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException {
            super.writeInternal(object, type, outputMessage);
            FieldFilterUtil.remove();
        }

    }
}
