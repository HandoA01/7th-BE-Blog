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
public class PostSummaryResponse {

    private Long id;
    private String title;
    private String authorNickname;
    private LocalDateTime createdAt;
}
