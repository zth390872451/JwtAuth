package com.qingxiao.jwt.service;


import com.qingxiao.jwt.entity.User;

import java.util.Optional;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
public interface UserService {
    public Optional<User> getByUsername(String username);
}
