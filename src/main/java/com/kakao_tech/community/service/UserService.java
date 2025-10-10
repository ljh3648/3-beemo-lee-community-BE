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
        // TODO : 예외처리 핸들링 필요.
        // 데이터베이스에서 유저 테이블에서 닉네임 컬럼에서 userDTO.getNickname()으로 해당 닉네임이 있는지 찾아본다.
        if (userRepository.existsByNickname(userDTO.getNickname())) {
            throw new IllegalArgumentException("중복된 유저 닉네임 입니다.");
        }

        // 중복된 이메일인지 검사한다.
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }

        User user = new User(userDTO.getNickname(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getProfileUrl());
        user = userRepository.save(user);

        return new UserDTO.SignUpResponse(user);
    }
}
