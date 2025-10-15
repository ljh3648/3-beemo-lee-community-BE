package com.kakao_tech.community.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleController {

    @GetMapping("/")
    ResponseEntity<Void> requestGetMethod() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    ResponseEntity<Void> requestPostMethod() {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    ResponseEntity<Void> requestPutMethod() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/")
    ResponseEntity<Void> requestDeleteMethod() {
        return ResponseEntity.ok().build();
    }
}
