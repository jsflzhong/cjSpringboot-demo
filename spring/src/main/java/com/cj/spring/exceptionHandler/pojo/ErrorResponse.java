package com.cj.spring.exceptionHandler.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private Integer code;

    private String message;

    private Object data;
}
