package com.kakao_tech.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class ErrorDTO {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private String code;
        private String message;
    }
}
