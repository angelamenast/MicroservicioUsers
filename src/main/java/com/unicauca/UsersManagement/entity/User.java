package com.unicauca.usersmanagement.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id")
    private List<Role> roles;

}
