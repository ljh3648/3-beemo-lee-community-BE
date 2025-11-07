package com.kakao_tech.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userId;

    @Column(unique = true)
    private String token;

    private Instant expiresAt;

    private boolean revoked;

    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id", columnDefinition = "INT")
    // private User user;
}