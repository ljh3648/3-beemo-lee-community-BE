package com.kakao_tech.community.controller;

import com.kakao_tech.community.dto.SessionDTO;
import com.kakao_tech.community.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 세션 등록 (로그인)
    @PostMapping("/sessions")
    public ResponseEntity<?> createSession(@RequestBody Map<String, String> body) {
        // TODO : 세션 진짜 개선 많이 해야함.
        // 일단 이메일 패스워드 확인하는 로직 필요할거 같고
        // 일치하면 일단 세션을 생성해서 뭐 데이터베이스에도 저장해야할 거 같고 근데 백만명이 사용하는 커뮤니티니까 더 생각해봐야할거같고
        // 그 다음에 세션을 헤더에 넣어서 전송해야할거 같고

        // 일단 서비스에 넘기자
        try {
            SessionDTO sessionDTO = authService.createSession(body.get("email"), body.get("password"));

            // 7. 세션 만들어진거 유저한테 넘겨야지 헤더로
            return ResponseEntity.status(201)
                    .header("Set-Cookie", "SESSION_KEY=" + sessionDTO.getSessionKey() + "; HttpOnly; Path=/")
                    .build();
        } catch (IllegalArgumentException e) {
            // TODO : 예외처리 추가 필요.
            return ResponseEntity.status(400).build();
        }
    }

    // 세션 파괴 (로그아웃)
    // 여기에서는 현재 접속된 세션을 삭제해야 함.
    // 그렇다면 헤드에서 세션값을 추출해서 세션이 유효한지 인증하는 과정이 필요함.
    // 일치하다면 데이터베이스에서 세션을 삭제함.
    // 그렇다면 사용자 웹에서 세션값이 담겨져있는 쿠키를 삭제하라는 명령이 필요한가? 는 조사가 필요할거 같음
    @DeleteMapping("/sessions")
    public ResponseEntity<?> deleteSession(@RequestHeader(value = "Cookie", required = false) String cookie) {
        if (cookie == null) {
            // 개선 필요
            // 이 로직도 컨트롤러에 있으면 안될거같은데
            return ResponseEntity.status(400).build();
        }

        // 쿠키값 파싱하기
        // 일단 출력 먼저 해봐야겠다.
        System.out.println("=================== COOKIE!! ===================");
        System.out.println("COOKIE: " + cookie);
        String sessionKey = cookie.split("SESSION_KEY=")[1];
        System.out.println(sessionKey);
        System.out.println("=================== ======== ===================");
//        =================== COOKIE!! ===================
//        SESSION_KEY=220769562
//        =================== ======== ===================
        // 파싱 작업도 서비스로 옮기던지 DTO 만들어서 하던지

        // 데이터베이스에서 세션 값 찾아보기
        // 찾았으면 삭제하기
        try {
            authService.deleteSession(sessionKey);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).build();
        }

        // 그런 유저 없으면 이미 로그아웃 된 것으로

        return ResponseEntity.status(204).header("Set-Cookie", "SESSION_ID=; Max-Age=0; Path=/; Secure; HttpOnly").build();
    }

}
