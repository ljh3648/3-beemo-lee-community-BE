package com.kakao_tech.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class SessionDTO {

    @Getter
    @Setter
    public static class RequestCookie {
        private String sessionKey;

        protected RequestCookie() {}

        public RequestCookie(String cookie) {
            // 쿠키를 파싱하는 과정이 필요함.
            // 쿠키가 null 이거나 공백인것을 먼저 잡는게 좋을거 같다.
            if (cookie.isEmpty()) {
                throw new IllegalArgumentException("쿠키 비어있음");
            }

            // 일단 split으로 영역을 나눈다.
            String[] cookieArray = cookie.split(";");

            for (String arr : cookieArray) {
                String[] keyValue = arr.trim().split("=");
                if (keyValue[0].equals("SESSION_KEY")) {
                    this.sessionKey = keyValue[1];
                    break;
                }
            }

            if (this.sessionKey == null) {
                throw new IllegalArgumentException("쿠키에 세션키 없음.");
            }
        }
    }

    @Getter
    @Setter
    public static class ResponseCookie {
        private String sessionKey;
        private LocalDateTime expiredAt;

        protected ResponseCookie() {}

        public ResponseCookie(String sessionKey, LocalDateTime expiredAt) {
            this.sessionKey = sessionKey;
            this.expiredAt = expiredAt;
        }
    }
}
