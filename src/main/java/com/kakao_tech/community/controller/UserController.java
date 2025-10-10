package com.kakao_tech.community.controller;

import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO.SignUpResponse> createUser(@RequestBody UserDTO.SignUpRequest userDTO) {
        // TODO : 추후에 전역 핸들러를 통해서 예외처리 필요, 서비스에서 여기는 정상 응답만 반환하기.
        // TODO : 예외처리에 대한 고민이 필요함.
        UserDTO.SignUpResponse result = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(result);
    }
}
