package com.example.blog.domain.comment.converter;

import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.user.entity.User;
import com.example.blog.dto.comment.CommentCreateRequest;
import com.example.blog.dto.comment.CommentResponse;

public class CommentConverter {

    // CommentCreateRequest + User + Post → Comment Entity
    public static Comment toEntity(CommentCreateRequest request, User user, Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .build();
    }

    // Comment Entity → CommentResponse
    public static CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .authorId(comment.getUser().getId())
                .authorNickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
