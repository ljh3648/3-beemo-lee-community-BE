package com.kakao_tech.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(unique = false, nullable = false, length = 12)
    private String title;

    @Column(unique = false, nullable = false, columnDefinition = "LONGTEXT")
    private String body;

    @Column(unique = false, nullable = false, length = 255)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "INT")
    private UserEntity user;

    protected PostEntity() {}

    public PostEntity(String title, String body, UserEntity user) {
        this.title = title;
        this.body = body;
        this.createdAt = LocalDateTime.now();

        this.user = user;
    }

    public PostEntity(String title, String body, String imageUrl, UserEntity user) {
        this(title, body, user);
        this.imageUrl = imageUrl;
    }
}
