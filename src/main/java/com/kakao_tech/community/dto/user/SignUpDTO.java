package com.kakao_tech.community.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class SignUpDTO {

    @Getter
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "{required.user.nickname}")
        private String nickname;

        @NotBlank(message = "{requir`required.user.password}")
        @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$", message = "{invalid.user.email}")
        @Size(max = 320, message = "{invalid.user.email}")
        private String email;

        @NotBlank(message = "{required.user.password}")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$", message = "{invalid.user.password}")
        private String password;
        private String profileUrl;

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
