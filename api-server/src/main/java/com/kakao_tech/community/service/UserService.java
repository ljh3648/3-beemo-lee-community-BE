package com.kakao_tech.community.service;

import com.kakao_tech.community.dto.ImageDTO;
import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO : 프로필 사진 어떻게 저장할건지?
    public UserDTO.SignUpResponse createUser(UserDTO.SignUpRequest userDTO) {
        if (userRepository.existsByNickname(userDTO.getNickname())) {
            throw new IllegalArgumentException("중복된 유저 닉네임 입니다.");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }

        User user = new User(
                userDTO.getNickname(),
                userDTO.getEmail(),
                BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()),
                userDTO.getProfileUrl()
                );
        user = userRepository.save(user);

        return new UserDTO.SignUpResponse(user);
    }
}
