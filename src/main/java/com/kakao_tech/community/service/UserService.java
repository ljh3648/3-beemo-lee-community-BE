package com.kakao_tech.community.service;

import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO.SignUpResponse createUser(UserDTO.SignUpRequest userDTO) {
        User user = new User(userDTO.getNickname(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getProfileUrl());
        user = userRepository.save(user);

        return new UserDTO.SignUpResponse(user);
    }
}
