package com.kakao_tech.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    // GET - '/posts?limit=10'
    // GET - '/posts?limit=10&offset=10'
    public ResponseEntity<PostDTO.ListResponse> getPosts(@RequestParam(defaultValue = "10") Pageable limit, @RequestParam(required = false) Long offset){
        PostDTO.ListResponse response = postService.getPosts(limit, offset);
        return ResponseEntity.ok(response);
    }
}
