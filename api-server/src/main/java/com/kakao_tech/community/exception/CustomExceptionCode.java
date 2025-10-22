package com.kakao_tech.community.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public enum CustomExceptionCode implements ExceptionCode {
    // 어떤 오류인지, 어떤 리소스인지?, 비고(선택)

    // 입력값 부재
    REQUIRED_NICKNAME(BAD_REQUEST, "REQUIRED_NICKNAME", "닉네임을 입력해주세요."),
    REQUIRED_EMAIL(BAD_REQUEST, "REQUIRED_EMAIL", "이메일을 입력해주세요."),
    REQUIRED_PASSWORD(BAD_REQUEST, "REQUIRED_PASSWORD", "비밀번호를 입력해주세요."),
    REQUIRED_PASSWORD_RE(BAD_REQUEST, "REQUIRED_PASSWORD_RE", "재확인 비밀번호를 입력해주세요."),
    // 중복 오류
    DUPLICATE_NICKNAME(BAD_REQUEST, "DUPLICATE_NICKNAME", "이미 등록된 닉네임이에요."),
    DUPLICATE_EMAIL(BAD_REQUEST, "DUPLICATE_EMAIL", "이미 등록된 이메일이에요."),
    // 양식 오류
    INVALID_NICKNAME(BAD_REQUEST, "INVALID_NICKNAME", "닉네임 형식이 올바르지 않아요."),
    INVALID_NICKNAME_LENGTH_OVER(BAD_REQUEST, "INVALID_NICKNAME_LENGTH_OVER", "닉네임은 최대 10자 이내이에요."),
    INVALID_EMAIL(BAD_REQUEST, "INVALID_EMAIL", "이메일 형식이 올바르지 않아요."),
    INVALID_EMAIL_LENGTH_OVER(BAD_REQUEST, "INVALID_EMAIL_LENGTH_OVER", "이메일은 최대 360자 이내이에요."),
    // 검증 오류
    // 틀린_패스워드_확인값
    DIFFERENT_PASSWORD_RE(BAD_REQUEST, "DIFFERENT_PASSWORD_RE","입력된 비밀번호와 재입력된 비밀번호가 달라요.");

    private HttpStatus httpStatus;
    private String code;
    private String message;

    CustomExceptionCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }


}
