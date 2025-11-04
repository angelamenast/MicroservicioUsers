package com.unicauca.UsersManagement.infra.dto;

import lombok.Getter;
import lombok.Setter;

public class HeadOfDepartmentEvent {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private UserRequest userRequest;
    @Getter @Setter
    private String suputamadre;
}
