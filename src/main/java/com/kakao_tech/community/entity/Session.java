package com.kakao_tech.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// TODO : 대충 만든거라 다시 세심하게 검토해볼 필요가 있음.
// + sessionKey는 유니크로 변경해야함. 키값이 겹치면 어떤 사용자로 로그인한건지 나중에 판단 불가능임.

@Entity
@Getter
@Setter
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(unique = false, nullable = false, length = 10)
    private String sessionKey;

    @Column(unique = false, nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(unique = false, nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "INT")
    private User user;

    protected Session() {}

    public Session(String sessionKey, User user) {
        this.sessionKey = sessionKey;
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusMinutes(30);

        this.user = user;
    }
}
