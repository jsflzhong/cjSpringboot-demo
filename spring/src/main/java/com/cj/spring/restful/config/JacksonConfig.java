package com.cj.spring.restful.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cj.spring.restful.util.FieldFilterUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        builder.failOnUnknownProperties(true);
        builder.filters(new SimpleFilterProvider().addFilter("field-filter", new DynamicPropertyFilter()));
        return builder.build();
    }

    class DynamicPropertyFilter extends SimpleBeanPropertyFilter {

        /**
         * Attention:
         *  1.Each fields in entity will call this function for 1 time!
         *  2.The case of fields in entity will be change to SNAKE_CASE automatically then return to FE, like: pruchaseTime-->pruchase_time (Based on the config above)
         *  3.Based on 2, the fields's value in the param of controller should be SNAKE_CASE, or can't pass the check:  needSerialize()
         *      like:http://localhost:8013/fields/test1?fields=id,name,pswd,pruchaseTime (NO, can't get the field:pruchaseTime)
         *      like:http://localhost:8013/fields/test1?fields=id,name,pswd,pruchase_time (YES)
         *
         *
         * @param pojo the pojo I return in controller
         * @param jgen
         * @param provider
         * @param writer Each fields in entity will call this function for 1time and encapesul into this writer.
         * @throws Exception
         */
        @Override
        public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider,
                                     PropertyWriter writer) throws Exception {
            if (FieldFilterUtil.get() == null) {
                FieldFilterUtil.apply(pojo.getClass());
            }
            String fieldsAsString = extractFieldsFromRequest(); //Get the param: fields's value in the request in controller.
            if (isFilterTarget(pojo) && hasFilterField(fieldsAsString) && !needSerialize(writer, fieldsAsString)) {
                return;
            }
            super.serializeAsField(pojo, jgen, provider, writer);
        }

        /**
         * Check if the param: fields's value in the request in controller is null
         * @param fieldsAsString
         * @return
         */
        private boolean hasFilterField(String fieldsAsString) {
            return !StringUtils.isEmpty(fieldsAsString);
        }

        private boolean isFilterTarget(Object pojo) {
            return pojo.getClass().equals(FieldFilterUtil.get());
        }

        /**
         * Check: If the  param: fields's value in the request in controller contain's the fields in entity we returned in controller
         * If it is, then include this field into the json we return, or else no.
         * @param writer
         * @param fieldsAsString
         * @return
         */
        private boolean needSerialize(PropertyWriter writer, String fieldsAsString) {
            return ArrayUtils.contains(StringUtils.split(fieldsAsString, ","), writer.getName());
        }

        private String extractFieldsFromRequest() {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            return requestAttributes.getRequest().getParameter("fields");
        }

    }
}
