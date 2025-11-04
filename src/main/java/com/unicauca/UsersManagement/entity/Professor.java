package com.unicauca.usersmanagement.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class Professor extends Person{
    @Getter
    @Setter
    private String office;

}
