package com.kakao_tech.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kakao_tech.community.dto.PostDTO;
import com.kakao_tech.community.dto.PostDTO.SummaryResponse;
import com.kakao_tech.community.entity.Post;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.repository.PostRepository;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    public PostDTO.ListResponse getPosts(Integer limit, Long offset) {
        // lastPostId가 NULL(값이 입력되지 않았으면) LONG타입의 맥스값 또는 입력 받은값으로 설정 (내림차순으로 가장 최근의 게시물을
        // 조회하기 위해서 기준이 필요함.)
        offset = (offset == null) ? Long.MAX_VALUE : offset;

        // Pageable 설정
        Pageable pageable = org.springframework.data.domain.PageRequest.of(0, limit);

        // 내림차순으로 게시물을 가져옴
        List<Post> posts = postRepository.findByIdLessThanOrderByIdDesc(offset, pageable);
        List<SummaryResponse> summaryPosts = new ArrayList<>();

        // 응답 DTO를 만들기 위해서 가져온 게시물로 매핑시작
        for (Post post : posts) {
            // post 엔티티에 양방향 설정되어 있어서 가능!!
            User user = post.getUser();
            PostDTO.Author author = PostDTO.Author.builder()
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .profileUrl(user.getProfileUrl())
                    .build();
            PostDTO.SummaryResponse summary = PostDTO.SummaryResponse.builder()
                    .author(author)
                    .id(post.getId())
                    .title(post.getTitle())
                    .viewsCnt(post.getViewsCnt())
                    .likesCnt(post.getLikesCnt())
                    .commentsCnt(post.getCommentCnt())
                    .createAt(post.getCreatedAt())
                    .updateAt(post.getUpdatedAt())
                    .build();

            summaryPosts.add(summary);
        }

        // 현재 포스트의 총 개수를 가져옴
        long postsTotalCount = postRepository.count();
        int postsGetCount = summaryPosts.size();
        // 마지막 게시물의 아이디를 저장, 0부터 시작 size - 1
        Long lastPostId = summaryPosts.isEmpty() ? null : summaryPosts.get(postsGetCount - 1).getId();

        // 응답 DTO 생성
        PostDTO.ListResponse response = PostDTO.ListResponse.builder()
                .posts(summaryPosts)
                .postsTotalCount(postsTotalCount)
                .postsGetCount(postsGetCount)
                .lastPostId(lastPostId)
                .build();

        return response;
    }

    @Transient
    public PostDTO.CreateResponse createPost(String title, String body, String imageUrl, User user) {
        Post post = new Post(title, body, imageUrl, user);
        post = postRepository.save(post);
        return new PostDTO.CreateResponse(post.getId());
    }
}
