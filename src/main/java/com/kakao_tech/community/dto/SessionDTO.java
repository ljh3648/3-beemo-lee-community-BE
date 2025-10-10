package com.kakao_tech.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionDTO {
    private String sessionKey;
    private LocalDateTime expiredAt;

    protected SessionDTO() {}

    public SessionDTO(String sessionKey, LocalDateTime expiredAt) {
        this.sessionKey = sessionKey;
        this.expiredAt = expiredAt;
    }
}
