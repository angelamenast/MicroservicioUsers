package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.Student;
import com.unicauca.usersmanagement.infra.dto.StudentRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IStudentService {
    @Transactional
    Student saveStudent(StudentRequest student) throws Exception;

    @Transactional
    Student saveStudent(Student student) throws Exception;

    @Transactional
    List<Student> findAllStudents() throws Exception;

    @Transactional
    public Student getStudentByEmail(String email);


}
