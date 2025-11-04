package com.unicauca.usersmanagement.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String phoneNumber;
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private EnumProgram program;
    @Getter @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id")
    private User user;

}
