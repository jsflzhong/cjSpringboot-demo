package com.cj.concurrent.exception;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by QC on 2018/7/20 16:45.
 */
@Component
public class ExceptionLauncherImpl implements ExceptionLauncher {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionLauncherImpl.class);

    /**
     * throw BusinessException(@msg)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public void throwBusinessException(String msg) throws BusinessException {
        logger.error(msg);
        throw new BusinessException(msg);
    }

    /**
     * if @obj is null,throw BusinessException(@msg)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public void checkArgumentNotNull(Object obj, String msg) throws BusinessException {
        if(!Optional.ofNullable(obj).isPresent())throwBusinessException(msg);
    }

    /**
     * if @expression is false,throw BusinessException(@msg)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public void checkArgument(Boolean expression, String msg) throws BusinessException {
        if(!expression) throwBusinessException(msg);
    }

    /**
     * if @return is neither null nor Empty,throw BusinessException(@Return)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public void checkArgument(Supplier<String> check) throws BusinessException {
        String msg = check.get();
        if(!Strings.isNullOrEmpty(msg))throwBusinessException(msg);
    }

    /**
     * throw BusinessException(@msg),
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public void throwBusinessException(String msg, Exception ex) throws BusinessException {
        logger.error(msg);
        BusinessException businessException = new BusinessException(msg);
        businessException.initCause(ex);
        throw businessException;
    }

    /**
     * return msg for error ,null for success
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public void checkArgument(Boolean expression, String msg, Exception ex) throws BusinessException {
        if(!expression) throwBusinessException(msg,ex);
    }


}
