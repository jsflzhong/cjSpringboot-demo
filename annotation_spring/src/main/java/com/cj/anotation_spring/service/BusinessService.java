package com.cj.anotation_spring.service;

import com.cj.anotation_spring.pojo.Entity;

import java.util.List;

/**
 * Created by Jian.Cui on 2018/11/7.
 *
 * @author Jian.Cui
 */
public interface BusinessService {

    void execute(List<? extends Entity> dataList);
}
