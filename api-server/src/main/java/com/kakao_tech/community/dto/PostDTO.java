package com.kakao_tech.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostDTO {
    public static class Author {
        private Integer id;
        private String nickname;
        private String profileUrl;
    }

    // 게시글 리스트 가져오기
    public static class ListResponse {
        private List<SummaryResponse> posts; // 간략한 게시글 조회 응답 리스트화.
        private Long postsTotalCount; // 커뮤니티에 존재하는 총 포스트 갯수.
        private Integer postsGetCount; // 리스트에 담긴 포스트 갯수.
        private Long lastPostId; // 마지막으로 전송된 포스트 PK 아이디.
    }

    public static class SummaryResponse {
        private Long id; // 게시물 PK 아이디
        private Author author; // 작성자 정보, 아이디, 닉네임, 프로필 이미지 주소
        private String title; // 게시글 제목
        private Integer viewsCnt;
        private Integer likesCnt;
        private Integer commentsCnt;
        private LocalDateTime createAt; // 게시글 작성일
        private LocalDateTime updateAt; // 게시글 마지막 수정일
    }

    public static class DetailResponse {
        private Long id; // 게시물 PK 아이디
        private Author author; // 작성자 정보, 아이디, 닉네임, 프로필 이미지 주소
        private String title; // 게시글 제목
        private String body;
        private Integer viewsCnt;
        private Integer likesCnt;
        private Integer commentsCnt;
        private LocalDateTime createAt; // 게시글 작성일
        private LocalDateTime updateAt; // 게시글 마지막 수정일
    }
}
