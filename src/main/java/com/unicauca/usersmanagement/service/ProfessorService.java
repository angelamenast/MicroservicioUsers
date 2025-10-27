package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.*;
import com.unicauca.usersmanagement.infra.dto.ProfessorEvent;
import com.unicauca.usersmanagement.infra.dto.ProfessorRequest;
import com.unicauca.usersmanagement.repository.ProfessorRepository;
import com.unicauca.usersmanagement.validation.LoginValidation;
import jakarta.transaction.Transactional;
import com.unicauca.usersmanagement.infra.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService implements IProfessorService {

    @Autowired
    private final ProfessorRepository professorRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private LoginValidation loginValidation;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    @Transactional
    public Professor saveProfessor(ProfessorRequest professor) throws Exception {
        try {
            Professor professorSaved = new Professor();
            professorSaved.setName(professor.getName());
            professorSaved.setLastName(professor.getLastName());
            professorSaved.setPhoneNumber(professor.getPhoneNumber());
            professorSaved.setProgram(EnumProgram.valueOf(professor.getProgram()));
            professorSaved.setOffice(professor.getOffice());

            User userSaved = new User();


            String passwordHash = loginValidation.encryptPassword(professor.getUserRequest().getPassword());

            userSaved.setEmail(professor.getUserRequest().getEmail());
            userSaved.setPassword(passwordHash);

            List<Role> rolesList = new ArrayList<>();

            for(String role : professor.getUserRequest().getRoles()){
                Role roleTemp = new Role();
                roleTemp.setRoleType(Enum.valueOf(EnumRole.class, role));
                rolesList.add(roleTemp);
            }

            userSaved.setRoles(rolesList);
            professorSaved.setUser(userSaved);

            professorSaved = professorRepository.save(professorSaved);

            ProfessorEvent professorSend = new ProfessorEvent();

            professorSend.setId(professorSaved.getId());
            professorSend.setName(professorSaved.getName());
            professorSend.setLastName(professorSaved.getLastName());
            professorSend.setPhoneNumber(professorSaved.getPhoneNumber());
            professorSend.setProgram(String.valueOf(professorSaved.getProgram()));
            professorSend.setUserRequest(professor.getUserRequest());
            professorSend.setOffice(professorSaved.getOffice());

            rabbitTemplate.convertAndSend(RabbitMQConfig.PROFESSOR_QUEUE,professorSend);

            return professorSaved;

        } catch (Exception e) {
            throw new Exception("Error saving professor: " + e.getMessage());
        }
    }

    @Override
    public Professor saveProfessor(Professor professor) throws Exception {
        try {
            String passwordHash = loginValidation.encryptPassword(professor.getUser().getPassword());
            professor.getUser().setPassword(passwordHash);
            return professorRepository.save(professor);
        } catch (Exception e) {
            throw new Exception("Error saving professor: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Professor> findAllProfessors() throws Exception {
        try {
            return professorRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error listing professors: " + e.getMessage());
        }
    }

}
