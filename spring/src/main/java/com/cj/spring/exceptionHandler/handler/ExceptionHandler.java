package com.cj.spring.exceptionHandler.handler;

import java.util.List;

import com.cj.spring.exceptionHandler.enums.ResultEnum;
import com.cj.spring.exceptionHandler.exception.BaseException;
import com.cj.spring.exceptionHandler.pojo.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuffer stringBuffer = new StringBuffer();
        if (allErrors != null) {
            int errorCount = allErrors.size();
            stringBuffer.append("VALIDATION_ERROR: [");
            for (int i = 0; i < errorCount; i++) {
                ObjectError objectError = allErrors.get(i);
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    stringBuffer.append("parameter: " + fieldError.getField() + ", cause: " + fieldError.getDefaultMessage());
                } else {
                    stringBuffer.append(objectError.getDefaultMessage());
                }
                if (i != errorCount - 1) {
                    stringBuffer.append(" | ");
                }
            }
            stringBuffer.append("]");
        }
        return this.generateErrorResponse(new BaseException(ResultEnum.FAILURE, stringBuffer.toString(), exception));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse otherExceptionHandler(Throwable exception) {
        logger.error("An Exception error has occurred.", exception);
        return this.generateErrorResponse(new BaseException(ResultEnum.FAILURE, exception.getMessage(), exception));
    }

    private ErrorResponse generateErrorResponse(BaseException exception) {
        logger.error("An Exception error has occurred.", exception);
        if (exception.getCode() != null) {
            return new ErrorResponse(exception.getCode(), exception.getMessage(), null);
        }
        return null;
    }
}
