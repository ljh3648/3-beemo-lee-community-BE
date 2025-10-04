package com.kakao_tech.community.dto;

import com.kakao_tech.community.entity.User;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {

    @Getter
    @Setter
    public static class SignUpRequset {
        private String nickname;
        private String email;
        private String password;

        protected SignUpRequset() {}

        public SignUpRequset(String nickname, String email, String password) {
            this.nickname = nickname;
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    @Setter
    public static class SignUpResponse {
        private Integer id;
        private String nickname;
        private String email;
        private String password;
        private String profileUrl;

        protected SignUpResponse() {}

        public SignUpResponse(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.profileUrl = user.getProfileUrl();
        }
    }
}
