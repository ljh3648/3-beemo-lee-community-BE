package com.kakao_tech.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "likes")
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "created_at", unique = false, nullable = true)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "INT")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", columnDefinition = "BIGINT")
    private Post post;

    protected  Like() {}

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
