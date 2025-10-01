package com.kakao_tech.community.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class UserEntityTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    void userCreateTest() {
        UserEntity user = new UserEntity("beemo", "beemo@example.com", "1234");
        entityManager.persist(user);
    }

}