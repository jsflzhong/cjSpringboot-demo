package com.cj.anotation_spring.service.Impl;

import com.cj.anotation_spring.annotation.EnumAnnotation;
import com.cj.anotation_spring.constant.TestEventEnum;
import org.springframework.stereotype.Component;

@Component
@EnumAnnotation({ TestEventEnum.EVO_APPROVED, TestEventEnum.EVO_CANCELED, TestEventEnum.EVO_REJECTED })
public class EnumAnnotationProcessor {

    //skip...
}
