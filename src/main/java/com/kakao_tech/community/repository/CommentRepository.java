package com.kakao_tech.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakao_tech.community.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
