package com.kakao_tech.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class SessionDTO {
    // 세션 생성을 위한 요청 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    public static class CreateRequest {
        private String email;
        private String password;
    }

    // 세션 생성 후 응답을 위한 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    public static class CreateResponse {
        private String sid;
    }
}
