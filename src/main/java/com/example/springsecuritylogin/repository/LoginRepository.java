package com.example.springsecuritylogin.repository;

import com.example.springsecuritylogin.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

    Optional<User> findByUniqueId(String uniqueId);

}
