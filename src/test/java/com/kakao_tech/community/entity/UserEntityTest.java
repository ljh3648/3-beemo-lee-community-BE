package com.kakao_tech.community.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class UserEntityTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    void userCreateTest() {
        User user = new User("beemo", "beemo@example.com", "1234");
        entityManager.persist(user);
    }

    @Test
    @Transactional
    @Rollback(false)
    void jpqlQueryTest() {
        System.out.println("test==================================");
        User user = new User("beemo", "beemo@example.com", "1234");
        entityManager.persist(user);
//        TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class);
//        List<UserEntity> users = query.getResultList();
        Query query = entityManager.createQuery("SELECT u.nickname FROM User u");
        List<Object[]> result = query.getResultList();

        System.out.println(result);
        System.out.println("result==================================");
    }

}