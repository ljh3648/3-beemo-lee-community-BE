package com.kakao_tech.community.service;

import java.beans.Transient;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.kakao_tech.community.dto.CommentDTO;
import com.kakao_tech.community.entity.Comment;
import com.kakao_tech.community.entity.Post;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.repository.CommentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // TODO: 댓글들 불러오기
    // Sql문을 여러번 날리는것보단 한번에 SQL한방으로 그런느낌으로 ㅇㅋㅇㅋ
    // public String getComments(Integer limit, Long offset) {
    // }

    // 댓글 가져오기
    public CommentDTO.Response getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        
        User user = comment.getUser();

        CommentDTO.Author author = 
            CommentDTO.Author.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();

        CommentDTO.Response response = 
            CommentDTO.Response.builder()
                .id(comment.getId())
                .author(author)
                .createAt(comment.getCreatedAt())
                .updateAt(comment.getUpdatedAt())
                .build();
        
        return response;
    }

    // 댓글 작성
    @Transient
    public CommentDTO.CreateResponse createComment(String body, User user, Post post) {

        // TODO: 댓글 작성일자 엔티티가 담당하게 하지말고 직접 컨트롤하게...
        Comment comment = new Comment(body, user, post);
        comment = commentRepository.save(comment);
    
        CommentDTO.CreateResponse response = new CommentDTO.CreateResponse(comment.getId());
        return response;
    }

    // @Transient
    // public String deleteComment(User user, Comment comment) {
    // }
}
