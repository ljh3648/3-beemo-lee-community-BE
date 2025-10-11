package com.kakao_tech.community.dto;

import com.kakao_tech.community.entity.User;
import lombok.Getter;
import lombok.Setter;

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
            // TODO : 비밀번호는 해시 알고리즘을 사용해서 저장해야함. #1
            this.password = password;
        }

        public SignUpRequest(String nickname, String email, String password, String profileUrl) {
            this(nickname, email, password);
            // 유저 이메일 검증 로직
            UserValidation.profileUrlValidation(profileUrl);
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
            // TODO : 비밀번호는 해시 알고리즘을 사용해서 저장해야함. #2
            this.password = password;
        }

        public void setProfileUrl(String profileUrl) {
            UserValidation.profileUrlValidation(profileUrl);
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

    /*
        TODO : 주석 멋지게 달아보고 싶다.
        유저의 정보 닉네임, 이메일, 비밀번호 양식을 검사하고
        이상이 있으면 IllegalArgumentException 반환을 이르킴
     */
    public static class UserValidation {
        // 닉네임 검증 로직
        public static void nicknameValidation(String nickname) {
            // 닉네임 null 또는 공백 검사
            if (nickname == null || nickname.isBlank()) {
                throw new IllegalArgumentException("닉네임 NULL 오류");
            }

            // 닉네임 길이 검사
            if(nickname.length() > 10) {
                throw new IllegalArgumentException("닉네임 글자는 최대 10글자 입니다.");
            }

            // TODO : 닉네임 검사 로직 완성 필요.
            // 닉네임: ㄱ <- 이러면 안됨.
            // 닉네임 숫자, 영어, 한글을 제외한 나머지는 안됨
        }

        // 이메일 검증 로직
        public static void emailValidation(String email) {
            // 이메일 null 또는 공백 검사
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("이메일 NULL 오류");
            }

            // 이메일 길이 검사
            if (email.length() > 320) {
                throw new IllegalArgumentException("이메일은 320자를 초과할 수 없습니다.");
            }

            // TODO : 이메일 검사 로직 완성 필요.
            // 이메일 양식이 맞는지 뭐 @가 있는지나 이메일 특수문자 되나?
            // 이메일은 숫자 영어만 몇몇개의 특수기호만 되지 않을까 싶다.
            // 그리고 유효한 도메인인지 확인도 필요할 듯
        }

        public static void passwordValidation(String password) {
            // 비밀번호 NULL 또는 공백 검사
            if (password == null || password.isBlank()) {
                throw new IllegalArgumentException("패스워드 NULL 오류.");
            }

            // TODO : 비밀번호 몇글자까지 허용할건지 확인 필요함.
            if (password.length() > 20) {
                throw new IllegalArgumentException("비밀번호는 X자를 초과할 수 없습니다.");
            }

            // TODO : 비밀번호 검사 로직 완성 필요.
            // 패스워드는 영어 대소문자 그리고 숫자 그리고 몇개의 특수문자만 되는걸로 아는데 이건
            // 도메인 마음대로 하면 될 듯.
        }

        public static void profileUrlValidation(String profileUrl) {
            // TODO : 프로필 주소 검증 로직이 필요하기도 하고, 지금은 아마 바이너리 값을 통째로 저장하려하지 않을까 싶음.
        }
    }
}