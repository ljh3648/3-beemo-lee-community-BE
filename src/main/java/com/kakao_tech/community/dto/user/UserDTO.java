package com.kakao_tech.community.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String nickname;
        private Integer userId;
        private String email;
        private String profileUrl;
    }
}