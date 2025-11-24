package com.kakao_tech.community.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // Vaild 어노테이션 검증 실패시 에러 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO.Response> handleValidationException(MethodArgumentNotValidException e) {

        // TODO: 에러 코드를 분석해서 적절한 AuthErrorCode 매핑
        ErrorDTO.Response errorResponse = new ErrorDTO.Response("VALIDATION_ERROR", "일단 이 메세지를 보면 얼른 코드 개선하러 와줘..");
        return ResponseEntity.status(400).body(errorResponse);
    }
}
