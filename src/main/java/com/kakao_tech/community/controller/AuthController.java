package com.kakao_tech.community.controller;

import com.kakao_tech.community.dto.SessionDTO;
import com.kakao_tech.community.dto.UserDTO;
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

    // TODO : 세션 진짜 개선 많이 해야함.
    // 세션 등록 (로그인)
    @PostMapping("/sessions")
    public ResponseEntity<?> createSession(@RequestBody Map<String, String> body) {

        try {
            UserDTO.SignInRequest userDTO = new UserDTO.SignInRequest(body.get("email"), body.get("password"));
            SessionDTO.ResponseCookie sessionDTO = authService.createSession(userDTO);

            return ResponseEntity.status(201)
                    .header("Set-Cookie", "SESSION_KEY=" + sessionDTO.getSessionKey() + "; HttpOnly; Path=/")
                    .build();
        } catch (IllegalArgumentException e) {
            // TODO : 예외처리 추가 필요.
            return ResponseEntity.status(400).build();
        }
    }

    // 세션 파괴 (로그아웃)
    @DeleteMapping("/sessions")
    public ResponseEntity<?> deleteSession(@RequestHeader(value = "Cookie", required = false) String cookie) {
        try {
            SessionDTO.RequestCookie sessionDTO = new SessionDTO.RequestCookie(cookie);
            authService.deleteSession(sessionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(204).header("Set-Cookie", "SESSION_ID=; Max-Age=0; Path=/; Secure; HttpOnly").build();
    }

}
