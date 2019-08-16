package com.cj.anotation_spring.controller;

import java.util.Map;

import com.cj.anotation_spring.constant.Identification;
import com.cj.anotation_spring.service.Period3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试管理抽象工厂模式的beans.
 * <p>
 * 坑: 问题:
 * 如果一个上层接口A有多个实现类, 那么当我用@Autowire注入一个map<String, 接口A>时, spring会自动把这些实现类都放入到这个map里, 把这个map当做一个bean供我们调用.
 * map的key固定就是实现类的Bean名字.
 * 所以, 如果在@Configuration中用@Bean自定义了一个和上面一样的自定义的key的map后,如果用@Autowire注入到其他类中,会发现拿不到这个自定义的map,而拿到的还是上面spring默认的map,
 * 即, key还是bean名. (已亲测)
 * 解决:
 * 所以,如果想拿到自己定义的那个map,需要在@Autowire字段上再用@Qualifier("自定义的bean map的bean 名字")才可以.
 */
@RestController
@RequestMapping("/p3")
@Slf4j
public class Period3Controller {

    @Autowired
    @Qualifier("period3ServiceMap") //注意要用该注解来指定要加载的bean. 否则会默认加载spring自己封装的map, 可那样map的key就不是我们自己能定义的了,而是bean的名字.
    Map<String, Period3Service> period3ServiceMap;

    @RequestMapping("/test1")
    public Object test1() {
        log.info(period3ServiceMap.toString());

        period3ServiceMap.get(Identification.PERIOD_03_a).process();  //@@@Period3Impl01 is running...

        period3ServiceMap.get(Identification.PERIOD_03_b).process();  //@@@Period3Impl02 is running...

        return 1;
    }
}
