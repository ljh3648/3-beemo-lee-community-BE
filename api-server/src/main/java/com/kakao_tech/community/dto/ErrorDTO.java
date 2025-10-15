package com.kakao_tech.community.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    private String error;
    private String message;

    protected ErrorDTO(){}

    public ErrorDTO(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
