package com.kakao_tech.community.service;

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

    public String uploadImage(MultipartFile image, String path, String fileName) {
        // TODO : 이미지를 검사한다.
        try {
            image.transferTo(
                    new File(
                    "/Users/beemo.cloud/Desktop/study/kakaotech-community/local/images/"
                            + path + fileName
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 생성된 이미지 주소를 전송한다.
        return path + fileName;
    }
}
