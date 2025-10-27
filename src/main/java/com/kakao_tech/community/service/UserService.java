package com.kakao_tech.community.service;

import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.exception.CustomErrorCode;
import com.kakao_tech.community.exception.RestApiException;
import com.kakao_tech.community.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    @Autowired
    public UserService(UserRepository userRepository, ImageService imageService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    public UserDTO.SignUpResponse createUser(UserDTO.SignUpRequest userDTO, MultipartFile profileImage) {
        if (userRepository.existsByNickname(userDTO.getNickname())) {
            throw new RestApiException(CustomErrorCode.DUPLICATE_NICKNAME);
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RestApiException(CustomErrorCode.DUPLICATE_EMAIL);
        }

        User user = new User(
                userDTO.getNickname(),
                userDTO.getEmail(),
                BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()),
                userDTO.getProfileUrl()
                );

        user = userRepository.save(user);

        if (profileImage != null) {
            user.setProfileUrl(
                    imageService.uploadImage(
                            profileImage,
                            "users/" + user.getId() + "/profile/",
                            "profile_"+ profileImage.getOriginalFilename()
                    )
            );
            userRepository.save(user);
        }


        return new UserDTO.SignUpResponse(user);
    }
}
