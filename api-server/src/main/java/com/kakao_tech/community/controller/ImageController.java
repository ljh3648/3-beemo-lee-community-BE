//// TODO : 폐기 예정
//
//package com.kakao_tech.community.controller;
//
//import com.kakao_tech.community.service.ImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/images")
//public class ImageController {
//    // 이미지 저장 서비스
//
//    public final ImageService imageService;
//
//    @Autowired
//    ImageController(ImageService imageService) {
//        this.imageService = imageService;
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadImage() {
//        // 이미지를 저장할 주소는? 서버가 정했으면 좋겠는데
//        // 음 근데 클라이언트한테 파일이름은 그렇다 치고, 파일이 저장될 경로를 정할 수 있게 하는게 말이 되나? 절대 안될거같은데?
//        // 그러면 회원가입은 어떡하지? 회원가입할떄 프로필 사진을 회원가입 컨트롤러에서 이 이미지 컨트롤러를 호출하는게 맞는걸까?
//        // 응답값은 204였나 POST 성공에 의미가 있는 그런게 있었을텐데.
//
//        // 파일 이름도 서버가 저장.
//        // 파일 경로도 서버가 저장.
//        // 이미지 업로드가 필요한 컨트롤러는 없을듯.
//        return ResponseEntity.status(201).build();
//    }
//}
