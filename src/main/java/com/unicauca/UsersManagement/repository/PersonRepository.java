package com.unicauca.usersmanagement.repository;

import com.unicauca.usersmanagement.entity.HeadOfDepartment;
import com.unicauca.usersmanagement.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {

    Person findByUser_Email(String email);


}
