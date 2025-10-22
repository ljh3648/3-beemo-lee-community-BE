package com.kakao_tech.community.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorDTO {
    private String code;
    private String message;

    protected ErrorDTO(){}

    public ErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
