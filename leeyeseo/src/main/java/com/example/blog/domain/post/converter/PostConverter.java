package com.example.blog.domain.post.converter;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.user.entity.User;
import com.example.blog.dto.post.PostCreateRequest;
import com.example.blog.dto.post.PostDetailResponse;
import com.example.blog.dto.post.PostSummaryResponse;

public class PostConverter {

    // PostCreateRequest + User → Post Entity
    public static Post toEntity(PostCreateRequest request, User user) {
        return Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .build();
    }

    // Post Entity → PostDetailResponse
    public static PostDetailResponse toDetailResponse(Post post) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .authorNickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // Post Entity → PostSummaryResponse (목록용)
    public static PostSummaryResponse toSummaryResponse(Post post) {
        return PostSummaryResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .authorNickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
