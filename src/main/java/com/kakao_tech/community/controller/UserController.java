package com.kakao_tech.community.controller;

import com.kakao_tech.community.dto.user.SignUpDTO;
import com.kakao_tech.community.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(
            @RequestBody Map<String, String> body,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        String accessToken = userService.signInUser(body.get("email"), body.get("password"), response);

        if (accessToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "아이디또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.status(201).body(Map.of("message", "로그인 성공"));
    }

    @PostMapping("/refresh")
    @ResponseBody
    public Map<String, String> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {

        if (refreshToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Map.of("error", "Refresh token missing");
        }

        try {
            var tokenRes = userService.refreshTokens(refreshToken, response);

            if (tokenRes == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return Map.of("error", "Refresh token invalid or expired");
            }

            return Map.of(
                    "accessToken", tokenRes.accessToken(),
                    "refreshToken", tokenRes.refreshToken());
        } catch (ResponseStatusException exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Map.of("error", "Refresh token invalid or expired");
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOutUserRefresh(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
        userService.signOutUser(response, refreshToken);
        return ResponseEntity.ok().body(Map.of("message", "로그아웃 성공"));
    }

    // 회원가입
    @PostMapping("/users")
    public ResponseEntity<?> createUser(
            @Valid @RequestPart(value = "user") SignUpDTO.Request user,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        SignUpDTO.Response result = userService.createUser(user, profileImage);

        return ResponseEntity.status(201).body(result);
    }

}
