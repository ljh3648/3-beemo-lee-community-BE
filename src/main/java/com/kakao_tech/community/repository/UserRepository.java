package com.kakao_tech.community.repository;

import com.kakao_tech.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
