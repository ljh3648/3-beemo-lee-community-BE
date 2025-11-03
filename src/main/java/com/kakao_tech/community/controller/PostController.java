package com.kakao_tech.community.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakao_tech.community.dto.PostDTO;
import com.kakao_tech.community.service.PostService;
import com.kakao_tech.community.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    public final PostService postService;
    public final UserService userService;

    @GetMapping
    // GET - '/posts?limit=10'
    // GET - '/posts?limit=10&offset=10'
    public ResponseEntity<PostDTO.ListResponse> getPosts(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", required = false) Long offset) {
        PostDTO.ListResponse response = postService.getPosts(limit, offset);
        return ResponseEntity.ok(response);
    }

    // 게시글 생성
    // TODO: 사진 여러장 받고 처리할 수 있도록 추가 필요함.
    @PostMapping
    public ResponseEntity<PostDTO.CreateResponse> createPost(@RequestAttribute("userId") Integer userid,
            @RequestBody PostDTO.CreateRequest request) {

        PostDTO.CreateResponse response = postService.createPost(request.getTitle(), request.getBody(),
                request.getImageUrl(), userService.getUser(userid));

        return ResponseEntity.status(201).body(response);
    }

}
