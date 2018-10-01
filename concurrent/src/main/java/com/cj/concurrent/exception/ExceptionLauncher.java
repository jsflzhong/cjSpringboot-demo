package com.cj.concurrent.exception;

import java.util.function.Supplier;

/**
 * Created by Jian.Cui on 2018/9/27.
 */
public interface ExceptionLauncher {
    void throwBusinessException(String msg) throws BusinessException;
    void checkArgumentNotNull(Object obj, String msg) throws BusinessException;
    void checkArgument(Boolean expression, String msg) throws BusinessException;
    void checkArgument(Supplier<String> check) throws BusinessException;
    void throwBusinessException(String msg, Exception ex) throws BusinessException;
    void checkArgument(Boolean expression, String msg, Exception ex) throws BusinessException;
}
