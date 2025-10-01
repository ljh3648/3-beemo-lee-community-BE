package com.kakao_tech.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(unique = false, nullable = false, length = 12, columnDefinition = "VARCHAR")
    private String title;

    @Column(unique = false, nullable = false, columnDefinition = "LONGTEXT")
    private String body;

    @Column(unique = false, nullable = false, length = 255, columnDefinition = "VARCHAR")
    private String imageUrl;

    @Column(unique = false, nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(unique = false, nullable = true, columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    @Column(unique = false, nullable = false, columnDefinition = "INT")
    private Integer viewsCnt;
    @Column(unique = false, nullable = false, columnDefinition = "INT")
    private Integer likesCnt;
    @Column(unique = false, nullable = false, columnDefinition = "INT")
    private Integer commentCnt;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "INT")
    private User user;

    protected Post() {}

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.createdAt = LocalDateTime.now();

        this.user = user;
    }

    public Post(String title, String body, String imageUrl, User user) {
        this(title, body, user);
        this.imageUrl = imageUrl;
    }
}
