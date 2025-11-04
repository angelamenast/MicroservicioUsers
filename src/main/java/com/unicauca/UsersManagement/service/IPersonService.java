package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.Person;
import org.springframework.transaction.annotation.Transactional;

public interface IPersonService {

    @Transactional
    Person findByUser_Email(String email);


}
