package com.kakao_tech.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(unique = false, nullable = false, length = 255, columnDefinition = "VARCHAR")
    private String body;

    @Column(unique = false, nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(unique = false, nullable = true, columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "INT")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", columnDefinition = "BIGINT")
    private Post post;

    protected Comment() {}

    public Comment(String body, User user, Post post) {
        this.body = body;
        this.createdAt = LocalDateTime.now();

        this.user = user;
        this.post = post;
    }
}
