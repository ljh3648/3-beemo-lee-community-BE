package com.kakao_tech.community.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SignUpDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        @Pattern(regexp = "^[0-9a-zA-Z가-힣]{2,10}$")
        private String nickname;

        @NotBlank
        @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        @Size(max = 320)
        private String email;

        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")
        private String password;

        // TODO: 사용자 약관 동의 여부도 포함.
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Integer userId;
        private String nickname;
        private String email;
        private String profileUrl;
    }
}
