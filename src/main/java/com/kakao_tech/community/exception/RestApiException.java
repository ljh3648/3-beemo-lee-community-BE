package com.kakao_tech.community.exception;

import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {

    final private AuthErrorCode authErrorCode;

    public RestApiException(AuthErrorCode authErrorCode) {
        this.authErrorCode = authErrorCode;
    }

}
