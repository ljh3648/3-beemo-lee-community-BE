package com.kakao_tech.community.controller;

import com.kakao_tech.community.dto.ErrorDTO;
import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    public final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body) {
        // 원래 DTO로 받았는데 그러면 try catch 문에서 예외처리가 안걸림.
        String nickname = body.get("nickname");
        String email = body.get("email");
        String password = body.get("password");
        String profileUrl = body.get("profileUrl");

        // TODO : 추후에 전역 핸들러를 통해서 예외처리 필요, 서비스에서 여기는 정상 응답만 반환하기.
        // TODO : 예외처리에 대한 고민이 필요함.
        try {
            UserDTO.SignUpRequest userDTO = new UserDTO.SignUpRequest(nickname, email, password, profileUrl);

            UserDTO.SignUpResponse result = userService.createUser(userDTO);
            return ResponseEntity.status(201).body(result);
        } catch (IllegalArgumentException e) {
            // 원래 여기에 바디에 이런식으로 하려고 했는데
            // "error": "ERROR_EMAIL_NULL",
            // "message": "이메일이 입력되지 않았습니다."
            // 일단 해보자
            ErrorDTO errorDTO;

            // 인자오류 응답 반환
            if (e.getMessage().equals("닉네임 NULL 오류")) {
                errorDTO = new ErrorDTO("ERROR_NICKNAME_IS_NULL", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("닉네임 글자는 최대 10글자 입니다.")) {
                errorDTO = new ErrorDTO("ERROR_NICKNAME_LENGTH_OVER", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("이메일 NULL 오류")) {
                errorDTO = new ErrorDTO("ERROR_EMAIL_IS_NULL", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("이메일은 320자를 초과할 수 없습니다.")) {
                errorDTO = new ErrorDTO("ERROR_EMAIL_LENGTH_OVER", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            // 중복 응답 반환
            if (e.getMessage().equals("중복된 유저 닉네임 입니다.")) {
                errorDTO = new ErrorDTO("ERROR_NICKNAME_DUPLICATION", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("중복된 이메일 입니다.")) {
                errorDTO = new ErrorDTO("ERROR_EMAIL_DUPLICATION", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            return ResponseEntity.status(500).body(null);
        }
    }
}
