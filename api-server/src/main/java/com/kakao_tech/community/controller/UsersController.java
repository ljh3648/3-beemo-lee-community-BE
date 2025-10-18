package com.kakao_tech.community.controller;

import com.kakao_tech.community.dto.ErrorDTO;
import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UsersController {

    public final UserService userService;

    @Autowired
    UsersController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 기능
    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body) {
        // 원래 DTO로 받았는데 그러면 try catch 문에서 예외처리가 안걸림.
        // TODO : 추후에 전역 핸들러를 통해서 예외처리 필요, 서비스에서 여기는 정상 응답만 반환하기.
        // TODO : 예외처리에 대한 고민이 필요함.
        try {
            UserDTO.SignUpRequest userDTO =
                    new UserDTO.SignUpRequest(
                            body.get("nickname"),
                            body.get("email"),
                            body.get("password"),
                            body.get("profileUrl")
                            );

            // 임시로 이미지 처리 구현 중
            // base64로 디코딩 해야함
            // 저장은 어디에다가 하지? 당연히 로컬 서버에 저장을 해둬야지 아니면 데이터베이스
            String image = body.get("profile");
            System.out.println(image);

            // 일단 가져오긴 했는데, 용량이 엄청 큰건 커트를 해야하는데 서버 자체적으로
            // 요청 응답 크기를 제한 할 수 있나?
            // 아니면, HTTP 통신으로 보낼 수 있는 용량 제한같은게 있지 않을까?

            // 1. 일단 서버에 들어오는 용량을 제한을 한다.
            // 서버는 요청을 받는다.
            // 서버는 요청 메세지 크기를 사전에 알아차린다.
            // 그런 로직이 있겠지 뭐
            // 그래서 그 요청 메세지가 너무 크면 예외처리로 나쁜 요청이라고 냅다 보낸다

            // 결론
            // 요청 크기를 어떻게 제한할건가?
            // 톰캣서버에서 알아서 걸러지도록 yml 설정. -> 그러면 전역적으로 설정됨
            // 필터를 통해서 걸러지도록 설정. -> 제일 베스트인듯
            // 인터레트로 걸러지도록 설정. -> 이러면 이미 바디를 읽어온 느낌인거 같아서 안될듯.



            UserDTO.SignUpResponse result = userService.createUser(userDTO);
            return ResponseEntity.status(201).body(result);
        } catch (IllegalArgumentException e) {
            ErrorDTO errorDTO;

            if (e.getMessage().equals("닉네임 NULL 오류")) {
                errorDTO = new ErrorDTO("ERROR_NICKNAME_IS_NULL", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("닉네임 글자는 최대 10글자 입니다.")) {
                errorDTO = new ErrorDTO("ERROR_NICKNAME_LENGTH_OVER", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("이메일 NULL 오류")) {
                errorDTO = new ErrorDTO("ERROR_EMAIL_IS_NULL", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("이메일은 320자를 초과할 수 없습니다.")) {
                errorDTO = new ErrorDTO("ERROR_EMAIL_LENGTH_OVER", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("중복된 유저 닉네임 입니다.")) {
                errorDTO = new ErrorDTO("ERROR_NICKNAME_DUPLICATION", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("중복된 이메일 입니다.")) {
                errorDTO = new ErrorDTO("ERROR_EMAIL_DUPLICATION", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("닉네임은 숫자, 영어, 한글 입력만 가능합니다.")) {
                errorDTO = new ErrorDTO("ERROR_NICKNAME", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("이메일 양식이 올바르지 않습니다.")) {
                errorDTO = new ErrorDTO("ERROR_EMAIL", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            if (e.getMessage().equals("비밀번호 양식이 올바르지 않습니다.")) {
                errorDTO = new ErrorDTO("ERROR_PASSWORD", e.getMessage());
                return ResponseEntity.status(400).body(errorDTO);
            }

            errorDTO = new ErrorDTO("ERROR_NOT_DEFINE", "예상하지 못한 서버 오류입니다.");
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
}
