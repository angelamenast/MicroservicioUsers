package com.unicauca.usersmanagement.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class HeadOfDepartment extends Person{
    @Getter
    @Setter
    private String suputamadre;

}
