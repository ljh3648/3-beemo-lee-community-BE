package com.kakao_tech.community.controller;

import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.service.NewUserService;
import com.kakao_tech.community.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
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
    public final NewUserService newUserService;

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(
            @RequestBody Map<String, String> body,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        String accessToken = newUserService.signInUser(body.get("email"), body.get("password"), response);

        if (accessToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "아이디또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok().body(Map.of("message", "로그인 성공"));
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
            var tokenRes = newUserService.refreshTokens(refreshToken, response);

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
    public ResponseEntity<?> signOutUserrefresh(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
        newUserService.signOutUser(response, refreshToken);
        return ResponseEntity.ok().body(Map.of("message", "로그아웃 성공"));
    }

    // 회원가입 기능
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestPart("user") Map<String, String> body,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        UserDTO.SignUpRequest userDTO = new UserDTO.SignUpRequest(
                body.get("nickname"),
                body.get("email"),
                body.get("password"));

        String image = body.get("profile");
        System.out.println(image);

        UserDTO.SignUpResponse result = userService.createUser(userDTO, profileImage);
        return ResponseEntity.status(201).body(result);
    }

}
