package com.kakao_tech.community.controller;

import com.kakao_tech.community.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // 세션 등록 (로그인)
    @PostMapping("/sessions")
    public ResponseEntity<?> createSession(@RequestBody Map<String, String> body) {
        // TODO : 음 헤더에 세션 값이 들어가도록 해야하는데 로직도 완성시키고
        // 일단 이메일 패스워드 확인하는 로직 필요할거 같고
        // 일치하면 일단 세션을 생성해서 뭐 데이터베이스에도 저장해야할 거 같고 근데 백만명이 사용하는 커뮤니티니까 더 생각해봐야할거같고
        // 그 다음에 세션을 헤더에 넣어서 전송해야할거 같고

        // 1. 일단 데이터베이스에서 유저테이블에서 입력받은 이메일 유저가 있는지 찾기
        // 1.1. 음 User레퍼지토리 이용해서 이미 그 비교하는거 있으니까 그거 쓰면 될거같은데
        // 1.2  비밀번호 일치하는지도 추가하고
        // 1.3 근데 여기서 하면 안되고, 뭐 Auth서비스 만들어야지

        // 2. 그리고 그 유저의 비밀번호를 입력받은 비밀번호랑 비교하기 물론 나중에 비크립트로 둘이 비교해야함

        // 3. 위에 아무튼 뭐가 잘못됐으면 뭐가 이상하다고 리턴해야겠지

        // 4. 근데 이메일과 비밀번호가 일치하면 이제 세션 만들어야지

        // 5. 세션 어떻게 만들지?

        // 6. 세션 만들어진거 데이터베이스에 저장해야지

        // 7. 세션 만들어진거 유저한테 넘겨야지 헤더로
        return ResponseEntity.ok().build();
    }

    // 세션 파괴 (로그아웃)
    @DeleteMapping("/sessions")
    public ResponseEntity<?> deleteSession() {
        // TODO : 여기도 완성시켜야 함
        return ResponseEntity.ok().build();
    }

}
