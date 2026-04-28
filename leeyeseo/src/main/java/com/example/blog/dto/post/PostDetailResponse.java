package com.example.blog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
