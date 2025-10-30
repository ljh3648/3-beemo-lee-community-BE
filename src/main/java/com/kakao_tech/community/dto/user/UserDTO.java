package com.kakao_tech.community.dto.user;

import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.exception.AuthErrorCode;
import com.kakao_tech.community.exception.RestApiException;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

public class UserDTO {
    @Getter
    public static class SignUpRequest {
        private String nickname;
        private String email;
        private String password;
        private String profileUrl;

        protected SignUpRequest() {}

        public SignUpRequest(String nickname, String email, String password) {
            // 아이디, 이메일, 비밀번호 검증 로직
            UserValidation.nicknameValidation(nickname);
            UserValidation.emailValidation(email);
            UserValidation.passwordValidation(password);

            this.nickname = nickname;
            this.email = email;
            this.password = password;
        }

        public SignUpRequest(String nickname, String email, String password, String profileUrl) {
            this(nickname, email, password);
            this.profileUrl = profileUrl;
        }

        public void setNickname(String nickname) {
            UserValidation.nicknameValidation(nickname);
            this.nickname = nickname;
        }

        public void setEmail(String email) {
            UserValidation.emailValidation(email);
            this.email = email;
        }

        public void setPassword(String password) {
            UserValidation.passwordValidation(password);
            this.password = password;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }
    }

    @Getter
    @Setter
    public static class SignUpResponse {
        private Integer id;
        private String nickname;
        private String email;
        private String profileUrl;

        protected SignUpResponse() {}

        public SignUpResponse(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.profileUrl = user.getProfileUrl();
        }
    }

    @Getter
    public static class SignInRequest {
        private String email;
        private String password;

        protected SignInRequest() {}

        public SignInRequest(String email, String password) {
            UserValidation.emailValidation(email);
            UserValidation.passwordValidation(password);

            this.email = email;
            this.password = password;
        }
    }

    // 닉네임 검증 로직
    public static class UserValidation {
        public static void nicknameValidation(String nickname) {
            // 닉네임 null 또는 공백 검사
            if (nickname == null || nickname.isBlank()) {
//                throw new IllegalArgumentException("닉네임 NULL 오류");
                throw new RestApiException(AuthErrorCode.REQUIRED_NICKNAME);
            }

            // 닉네임 길이 검사
            if(nickname.length() > 10) {
//                throw new IllegalArgumentException("닉네임 글자는 최대 10글자 입니다.");
                throw new RestApiException(AuthErrorCode.INVALID_NICKNAME);
            }

            if(!Pattern.matches("[0-9a-zA-Z가-힣]*$", nickname)) {
//                throw new IllegalArgumentException("닉네임은 숫자, 영어, 한글 입력만 가능합니다.");
                throw new RestApiException(AuthErrorCode.INVALID_NICKNAME);
            }
        }

        public static void emailValidation(String email) {
            // 이메일 null 또는 공백 검사
            if (email == null || email.isBlank()) {
//                throw new IllegalArgumentException("이메일 NULL 오류");
                throw new RestApiException(AuthErrorCode.REQUIRED_EMAIL);
            }

            // 이메일 길이 검사
            if (email.length() > 320) {
//                throw new IllegalArgumentException("이메일은 320자를 초과할 수 없습니다.");
                throw new RestApiException(AuthErrorCode.INVALID_EMAIL);
            }

            if (!Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", email)) {
//                throw new IllegalArgumentException("이메일 양식이 올바르지 않습니다.");
                throw new RestApiException(AuthErrorCode.INVALID_EMAIL);
            }
        }

        public static void passwordValidation(String password) {
            // 비밀번호 NULL 또는 공백 검사
            if (password == null || password.isBlank()) {
//                throw new IllegalArgumentException("패스워드 NULL 오류.");
                throw new RestApiException(AuthErrorCode.REQUIRED_PASSWORD);
            }

            if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",  password)) {
//                throw new IllegalArgumentException("비밀번호 양식이 올바르지 않습니다.");
                throw new RestApiException(AuthErrorCode.INVALID_PASSWORD);
            }
        }
    }
}