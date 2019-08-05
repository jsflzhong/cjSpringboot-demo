package com.cj.mybatis.controller;

import java.util.stream.Collectors;

import com.cj.mybatis.exception.IllegalParamException;
import org.springframework.validation.BindingResult;

public class BaseController {

    protected void checkParam(BindingResult result) {
        if (result.hasErrors()) {
            String error = result.getAllErrors().stream()
                    .map(objectError -> "[" + objectError.getDefaultMessage() + "]")
                    .collect(Collectors.toList()).toString();
            throw new IllegalParamException(String.format("error in checkParam: %s", error));
        }
    }
}
