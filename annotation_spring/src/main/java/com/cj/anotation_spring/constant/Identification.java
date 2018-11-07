package com.cj.anotation_spring.constant;

/**
 * Created by Jian.Cui on 2018/11/7.
 *
 * @author Jian.Cui
 */
public class Identification {

    //模拟唯一标识(代付渠道). 该值有两处必须对应: 在入口Controller中获取时 = 在注解的Handler类头上的注解中的value值.
    public static final String IDENTIFICATION_01 = "service01";
    public static final String IDENTIFICATION_02 = "service02";
}
