package com.kakao_tech.community.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kakao_tech.community.dto.ErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorDTO.Response> restApiExceptionHandler(RestApiException e) {
        AuthErrorCode errorCode = e.getAuthErrorCode();
        ErrorDTO.Response errorResponse = new ErrorDTO.Response(errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

}
