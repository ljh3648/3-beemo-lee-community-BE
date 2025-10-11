package com.kakao_tech.community.repository;

import com.kakao_tech.community.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findBySessionKey(String sessionKey);
    boolean existsBySessionKey(String sessionKey);
}
