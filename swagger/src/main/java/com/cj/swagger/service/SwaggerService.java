package com.cj.swagger.service;

import com.cj.swagger.entity.User;

public interface SwaggerService {

    User getById(int id);

    User getByIdAndName(int id, String name);

    User getById(User user);
}
