package com.unicauca.usersmanagement.service;

import com.unicauca.usersmanagement.entity.*;
import com.unicauca.usersmanagement.infra.dto.CoordinatorRequest;
import com.unicauca.usersmanagement.repository.CoordinatorRepository;
import com.unicauca.usersmanagement.validation.LoginValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinatorService implements ICoordinatorService {

    @Autowired
    private final CoordinatorRepository coordinatorRepository;
    @Autowired
    private LoginValidation loginValidation;

    public CoordinatorService(CoordinatorRepository coordinatorRepository) {
        this.coordinatorRepository = coordinatorRepository;
    }

    @Override
    @Transactional
    public Coordinator saveCoordinator(CoordinatorRequest coordinator) throws Exception {
        try {
            Coordinator coordinatorSaved = new Coordinator();

            coordinatorSaved.setName(coordinator.getName());
            coordinatorSaved.setLastName(coordinator.getLastName());
            coordinatorSaved.setPhoneNumber(coordinator.getPhoneNumber());
            coordinatorSaved.setProgram(EnumProgram.valueOf(coordinator.getProgram()));
            coordinatorSaved.setNombredelperro(coordinator.getNombredelperro());

            User userSaved = new User();

            String passwordHash = loginValidation.encryptPassword(coordinator.getUserRequest().getPassword());

            userSaved.setEmail(coordinator.getUserRequest().getEmail());
            userSaved.setPassword(passwordHash);

            List<Role> rolesList = new ArrayList<>();

            for(String role : coordinator.getUserRequest().getRoles()){
                Role roleTemp = new Role();
                roleTemp.setRoleType(Enum.valueOf(EnumRole.class, role));
                rolesList.add(roleTemp);
            }

            userSaved.setRoles(rolesList);

            coordinatorSaved.setUser(userSaved);

            return coordinatorRepository.save(coordinatorSaved);
        } catch (Exception e) {
            throw new Exception("Error saving coordinator: " + e.getMessage());
        }
    }

    @Override
    public Coordinator saveCoordinator(Coordinator coordinator) throws Exception {
        try {
            String passwordHash = loginValidation.encryptPassword(coordinator.getUser().getPassword());
            coordinator.getUser().setPassword(passwordHash);
            return coordinatorRepository.save(coordinator);
        } catch (Exception e) {
            throw new Exception("Error saving coordinator: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Coordinator> findAllCoordinators() throws Exception {
        try{
            return coordinatorRepository.findAll();
        }catch(Exception e){
            throw new Exception("Error listing coordinators: " + e.getMessage());
        }
    }

}



