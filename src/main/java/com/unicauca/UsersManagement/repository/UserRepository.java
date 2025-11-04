package com.unicauca.usersmanagement.repository;

import com.unicauca.usersmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
