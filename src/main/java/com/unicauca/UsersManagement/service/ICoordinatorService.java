package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.Coordinator;
import com.unicauca.usersmanagement.infra.dto.CoordinatorRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICoordinatorService {
    @Transactional
    Coordinator saveCoordinator(CoordinatorRequest coordinator) throws Exception;

    @Transactional
    Coordinator saveCoordinator(Coordinator coordinator) throws Exception;

    @Transactional
    List<Coordinator> findAllCoordinators() throws Exception;
}
