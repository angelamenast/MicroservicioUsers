package com.unicauca.usersmanagement.repository;

import com.unicauca.usersmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findByUser_Email(String email);

}
