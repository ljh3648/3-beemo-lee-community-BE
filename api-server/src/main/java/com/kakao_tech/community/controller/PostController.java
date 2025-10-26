package com.kakao_tech.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakao_tech.community.dto.PostDTO;
import com.kakao_tech.community.service.PostService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    public final PostService postService;

    @GetMapping
    // /posts?limit=10&lastPostId=10
    public ResponseEntity<PostDTO.ListResponse> getPosts(@RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) Long lastPostId){
        // 게시물 리스트 받기
        return ResponseEntity.status(200).build();
    }
}
