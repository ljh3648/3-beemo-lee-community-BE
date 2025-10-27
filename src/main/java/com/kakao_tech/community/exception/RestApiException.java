package com.kakao_tech.community.exception;

import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {

    final private CustomErrorCode customErrorCode;

    public RestApiException(CustomErrorCode customErrorCode) {
        this.customErrorCode = customErrorCode;
    }

}
