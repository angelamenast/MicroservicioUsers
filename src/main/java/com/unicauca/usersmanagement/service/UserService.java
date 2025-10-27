package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.*;
import com.unicauca.usersmanagement.infra.dto.ProfessorRequest;
import com.unicauca.usersmanagement.repository.ProfessorRepository;
import com.unicauca.usersmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<User> findAllUsers() throws Exception {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error listing professors: " + e.getMessage());
        }
    }

}
