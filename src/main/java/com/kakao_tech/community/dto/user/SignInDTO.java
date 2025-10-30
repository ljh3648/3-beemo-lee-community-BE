package com.kakao_tech.community.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SignInDTO {

    @Getter
    @AllArgsConstructor
    public static class Request {
        private String email;
        private String password;
    }
    
}