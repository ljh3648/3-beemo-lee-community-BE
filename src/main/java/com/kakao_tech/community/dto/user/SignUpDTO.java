package com.kakao_tech.community.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class SignUpDTO {

    @Getter
    @AllArgsConstructor
    public static class Request {
        private String nickname;
        private String email;
        private String password;
        private String profileUrl;

        // TODO: 사용자 약관 동의 여부도 포함.
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SignUpResponse {
        private Integer id;
        private String nickname;
        private String email;
        private String profileUrl;
    }

}
