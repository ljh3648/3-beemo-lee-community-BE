package com.kakao_tech.community.controller;

import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.repository.UserRepository;
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
    public ResponseEntity<UserDTO.SignUpResponse> createUser(@RequestBody UserDTO.SignUpRequset userDTO) {
        UserDTO.SignUpResponse result  = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(result);
    }

}
