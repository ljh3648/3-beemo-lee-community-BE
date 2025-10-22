package com.kakao_tech.community.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
