package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.*;
import com.unicauca.usersmanagement.infra.dto.StudentEvent;
import com.unicauca.usersmanagement.infra.dto.StudentRequest;
import com.unicauca.usersmanagement.repository.StudentRepository;
import jakarta.transaction.Transactional;
import com.unicauca.usersmanagement.infra.config.RabbitMQConfig;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.unicauca.usersmanagement.validation.*;

@Service
public class StudentService implements IStudentService{

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private LoginValidation loginValidation;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public Student saveStudent(StudentRequest student) throws Exception {
        try {
            Student studentSaved = new Student();
            studentSaved.setName(student.getName());
            studentSaved.setLastName(student.getLastName());
            studentSaved.setPhoneNumber(student.getPhoneNumber());
            studentSaved.setProgram(EnumProgram.valueOf(student.getProgram()));
            studentSaved.setStudentCode(student.getStudentCode());

            User userSaved = new User();

            String passwordHash = loginValidation.encryptPassword(student.getUserRequest().getPassword());

            userSaved.setEmail(student.getUserRequest().getEmail());
            userSaved.setPassword(passwordHash);

            List<Role> rolesList = new ArrayList<>();

            for(String role : student.getUserRequest().getRoles()){
                Role roleTemp = new Role();
                roleTemp.setRoleType(Enum.valueOf(EnumRole.class, role));
                rolesList.add(roleTemp);
            }

            userSaved.setRoles(rolesList);
            studentSaved.setUser(userSaved);

            studentSaved = studentRepository.save(studentSaved);

            StudentEvent studentSend = new StudentEvent();

            studentSend.setId(studentSaved.getId());
            studentSend.setName(studentSaved.getName());
            studentSend.setLastName(studentSaved.getLastName());
            studentSend.setPhoneNumber(studentSaved.getPhoneNumber());
            studentSend.setProgram(String.valueOf(studentSaved.getProgram()));
            studentSend.setUserRequest(student.getUserRequest());
            studentSend.setStudentCode(studentSaved.getStudentCode());

            rabbitTemplate.convertAndSend(RabbitMQConfig.STUDENT_QUEUE,studentSend);

            return studentSaved;
        } catch (Exception e) {
            throw new Exception("Error saving student: " + e.getMessage());
        }

    }

    @Override
    public Student saveStudent(Student student) throws Exception {
        try {
            String passwordHash = loginValidation.encryptPassword(student.getUser().getPassword());
            student.getUser().setPassword(passwordHash);
            return studentRepository.save(student);
        } catch (Exception e) {
            throw new Exception("Error saving student: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Student> findAllStudents() throws Exception {
        try{
            return studentRepository.findAll();
        }catch(Exception e){
            throw new Exception("Error listing students: " + e.getMessage());
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findByUser_Email(email);
    }



}
