package com.example.springsecuritylogin.repository;

import com.example.springsecuritylogin.domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

}
