package com.kakao_tech.community.repository;

import com.kakao_tech.community.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // SELECT * FROM posts WHERE id < {lastPostId} ORDER BY id DESC LIMIT {limit}
    List<Post> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
}
