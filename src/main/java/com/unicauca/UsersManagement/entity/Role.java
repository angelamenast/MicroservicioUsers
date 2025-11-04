package com.unicauca.usersmanagement.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class Role {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private EnumRole roleType ;

}
