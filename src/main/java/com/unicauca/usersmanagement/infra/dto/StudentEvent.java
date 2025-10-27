package com.unicauca.usersmanagement.infra.dto;

import lombok.Getter;
import lombok.Setter;

public class StudentEvent {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String phoneNumber;
    @Getter @Setter
    private String program;
    @Getter @Setter
    private UserRequest userRequest;
    @Getter @Setter
    private String studentCode;

}
