package com.kakao_tech.community.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakao_tech.community.dto.PostDTO;
import com.kakao_tech.community.dto.SessionDTO;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.service.AuthService;
import com.kakao_tech.community.service.PostService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    public final PostService postService;

    @Autowired
    public final AuthService authService;

    @GetMapping
    // GET - '/posts?limit=10'
    // GET - '/posts?limit=10&offset=10'
    public ResponseEntity<PostDTO.ListResponse> getPosts(@RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) Long offset) {
        PostDTO.ListResponse response = postService.getPosts(limit, offset);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDTO.CreateRequest request,
            @RequestHeader(value = "Cookie", required = false) String cookie) {
        // 세션 인증 후, user 객체 넘기기
        SessionDTO.RequestCookie session = new SessionDTO.RequestCookie(cookie);
        User user = authService.getCurrentUser(session);

        // 게시글 작성 서비스 호출
        Long postId = postService.createPost(request.getTitle(), request.getBody(), request.getImageUrl(), user);

        return ResponseEntity
            .created(URI.create("/api/posts/" + postId))
            .build();
    }

}
