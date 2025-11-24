package com.kakao_tech.community.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
    @Value("${app.upload-dir:./images/}")
    private String uploadDir;

    public ImageService() {
    }

    public String uploadImage(MultipartFile image, String path, String fileName) {
        // TODO : 이미지를 검사한다.
        try {
            // 프로젝트 실행 디렉토리 기반 절대 경로
            String basePath = System.getProperty("user.dir");
            String fullPath = basePath + File.separator + uploadDir + path;
            File dir = new File(fullPath);

            // 폴더가 없으면 생성
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(fullPath + fileName);
            image.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 생성된 이미지 주소를 전송한다.
        return uploadDir + path + fileName;
    }
}
