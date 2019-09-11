package com.cj.spring.argumentInject.config;

import java.util.Date;

import com.cj.spring.common.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 动态的为Controller层的指定的形参(对象)注入实例化后的对象.
 * 使得这类形参不用从前端传入值,就可以获得值.
 * 只有需要该对象的方法才定义该形参, 否则可以不定义,就不会消耗性能.
 *
 * 这样对开发来讲很方便, 可以免得手动查DB一次.
 */
@Component
public class ControllerArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果目标的形参类型是User,才执行下面方法的逻辑.
        if (parameter.getParameterType() == User.class) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //模拟从DB等途径获取数据,并返回.
        return new User().setAge(1).setBirthday(new Date()).setId(1).setName("myName");
    }
}
