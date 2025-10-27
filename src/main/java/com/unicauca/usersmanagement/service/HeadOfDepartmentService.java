package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.*;
import com.unicauca.usersmanagement.infra.dto.HeadOfDepartmentRequest;
import com.unicauca.usersmanagement.repository.HeadOfDepartmentRepository;
import com.unicauca.usersmanagement.validation.LoginValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeadOfDepartmentService implements IHeadOfDepartmentService {

    @Autowired
    private final HeadOfDepartmentRepository headRepository;
    @Autowired
    private LoginValidation loginValidation;

    public HeadOfDepartmentService(HeadOfDepartmentRepository headRepository) {
        this.headRepository = headRepository;
    }

    @Override
    @Transactional
    public HeadOfDepartment saveHead(HeadOfDepartmentRequest head) throws Exception {
        try {
            HeadOfDepartment headSaved = new HeadOfDepartment();

            headSaved.setName(head.getName());
            headSaved.setLastName(head.getLastName());
            headSaved.setPhoneNumber(head.getPhoneNumber());
            headSaved.setProgram(EnumProgram.valueOf(head.getProgram()));
            headSaved.setSuputamadre(head.getSuputamadre());

            User userSaved = new User();

            String passwordHash = loginValidation.encryptPassword(head.getUserRequest().getPassword());

            userSaved.setEmail(head.getUserRequest().getEmail());
            userSaved.setPassword(passwordHash);

            List<Role> rolesList = new ArrayList<>();

            for(String role : head.getUserRequest().getRoles()){
                Role roleTemp = new Role();
                roleTemp.setRoleType(Enum.valueOf(EnumRole.class, role));
                rolesList.add(roleTemp);
            }

            userSaved.setRoles(rolesList);

            headSaved.setUser(userSaved);

            return headRepository.save(headSaved);

        } catch (Exception e) {
            throw new Exception("Error saving head of department: " + e.getMessage());
        }

    }

    @Override
    public HeadOfDepartment saveHead(HeadOfDepartment head) throws Exception {
        try {
            String passwordHash = loginValidation.encryptPassword(head.getUser().getPassword());
            head.getUser().setPassword(passwordHash);
            return  headRepository.save(head);
        } catch (Exception e) {
            throw new Exception("Error saving head of department: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<HeadOfDepartment> findAllHeads() throws Exception {
        try{
            return headRepository.findAll();
        }catch(Exception e){
            throw new Exception("Error listing heads: " + e.getMessage());
        }
    }
}
