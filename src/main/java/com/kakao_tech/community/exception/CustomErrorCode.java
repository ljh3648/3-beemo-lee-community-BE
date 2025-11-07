package com.kakao_tech.community.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public enum CustomErrorCode implements ErrorCode {
    // 어떤 오류인지, 어떤 리소스인지?, 비고(선택)
    // 로그인 에러를 숨기고 싶을때 사용하는 에러
    ERROR_SIGN_IN(BAD_REQUEST, "ERROR_SIGN_IN", "이메일과 비밀번호를 확인해주세요."),

    // 입력값 부재
    REQUIRED_SIGN_IN(BAD_REQUEST, "REQUIRED_SIGN_IN", "로그인이 필요해요."),
    REQUIRED_NICKNAME(BAD_REQUEST, "REQUIRED_NICKNAME", "닉네임을 입력해주세요."),
    REQUIRED_EMAIL(BAD_REQUEST, "REQUIRED_EMAIL", "이메일을 입력해주세요."),
    REQUIRED_PASSWORD(BAD_REQUEST, "REQUIRED_PASSWORD", "비밀번호를 입력해주세요."),
    REQUIRED_PASSWORD_CONFIRM(BAD_REQUEST, "REQUIRED_PASSWORD_CONFIRM", "재확인 비밀번호를 입력해주세요."),
    // 중복 오류
    DUPLICATE_NICKNAME(BAD_REQUEST, "DUPLICATE_NICKNAME", "이미 등록된 닉네임이에요."),
    DUPLICATE_EMAIL(BAD_REQUEST, "DUPLICATE_EMAIL", "이미 등록된 이메일이에요."),
    // 양식 오류
    INVALID_NICKNAME(BAD_REQUEST, "INVALID_NICKNAME", "닉네임 형식이 올바르지 않아요."),
    INVALID_NICKNAME_LENGTH_OVER(BAD_REQUEST, "INVALID_NICKNAME_LENGTH_OVER", "닉네임은 최대 10자 이내이에요."),
    INVALID_EMAIL(BAD_REQUEST, "INVALID_EMAIL", "이메일 형식이 올바르지 않아요."),
    INVALID_EMAIL_LENGTH_OVER(BAD_REQUEST, "INVALID_EMAIL_LENGTH_OVER", "이메일은 최대 360자 이내이에요."),
    INVALID_PASSWORD(BAD_REQUEST, "INVALID_PASSWORD", "비밀번호 형식이 올바르지 않아요."),
    INVALID_SESSIONID(BAD_REQUEST, "INVALID_SESSIONID", "로그인이 필요해요."),
    // 검증 오류
    // 틀린_패스워드_확인값
    DIFFERENT_PASSWORD_CONFIRM(BAD_REQUEST, "DIFFERENT_PASSWORD_CONFIRM","입력된 비밀번호와 확인 비밀번호가 달라요."),
    DIFFERENT_SIGN_INFO(BAD_REQUEST, "DIFFERENT_SIGN_INFO","입력된 정보가 올바르지 않아요.");

    private HttpStatus httpStatus;
    private String code;
    private String message;

    CustomErrorCode(HttpStatus httpStatus, String code, String message) {
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
