package com.kakao_tech.community.service;

import com.kakao_tech.community.dto.ImageDTO;
import com.kakao_tech.community.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }


    // TODO : Image 엔티티 및 DTO 만들기 그리고 반환값 DTO로
    public void uploadImage(MultipartFile profileImage) {
        // TODO : 이미지를 검사한다.
            // 이미지 확장자는 'image/jpeg', 'image/jpg', 'image/png'
            // 파일 크기는 5MB 이하
        System.out.println("이미지 검사!!");
        System.out.println("original profile image content: " + profileImage.getContentType());
        System.out.println("original profile image size: " + profileImage.getSize());
        System.out.println("original profile image name: " + profileImage.getOriginalFilename());
        System.out.println("original profile image resource: " + profileImage.getResource());
        // 이미지를 로컬에 저장한다.
        try {
            profileImage.transferTo(new File("/Users/beemo.cloud/Desktop/study/kakaotech-community/local/images/test.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 이미지주소를 데이터베이스에 저장한다.
        // 생성된 이미지 주소를 전송한다.
        return ;
    }
}
