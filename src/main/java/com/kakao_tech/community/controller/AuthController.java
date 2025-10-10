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
            return ResponseEntity.status(400).build();
        }
    }

    // 세션 파괴 (로그아웃)
    @DeleteMapping("/sessions")
    public ResponseEntity<?> deleteSession() {
        // TODO : 여기도 완성시켜야 함
        return ResponseEntity.ok().build();
    }

}
