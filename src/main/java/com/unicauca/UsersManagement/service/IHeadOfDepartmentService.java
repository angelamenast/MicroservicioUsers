package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.HeadOfDepartment;
import com.unicauca.usersmanagement.infra.dto.HeadOfDepartmentRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IHeadOfDepartmentService {
    @Transactional
    HeadOfDepartment saveHead(HeadOfDepartmentRequest head) throws Exception;

    @Transactional
    HeadOfDepartment saveHead(HeadOfDepartment head) throws Exception;

    @Transactional
    List<HeadOfDepartment> findAllHeads() throws Exception;

}
