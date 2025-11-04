package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.Professor;
import com.unicauca.usersmanagement.infra.dto.ProfessorRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IProfessorService {

    @Transactional
    Professor saveProfessor(ProfessorRequest professor) throws Exception;

    @Transactional
    Professor saveProfessor(Professor professor) throws Exception;

    @Transactional
    List<Professor> findAllProfessors() throws Exception;

}
