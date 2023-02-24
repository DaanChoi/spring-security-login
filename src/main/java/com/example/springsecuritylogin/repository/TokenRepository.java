package com.example.springsecuritylogin.repository;

import com.example.springsecuritylogin.domain.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser_Id(Long userIdx);
}
