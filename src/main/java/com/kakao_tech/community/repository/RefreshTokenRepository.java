package com.kakao_tech.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao_tech.community.entity.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndRevokedFalse(String token);
    void deleteByUserId(Integer userId);
}