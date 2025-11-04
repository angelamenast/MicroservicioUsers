package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {
    @Transactional
    List<User> findAllUsers() throws Exception;
}
