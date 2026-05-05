package com.example.blog.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 목록 조회 응답 DTO")
public class PostSummaryResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long id;

    @Schema(description = "게시글 제목", example = "첫 게시글입니다")
    private String title;

    @Schema(description = "작성자 닉네임", example = "yeseo")
    private String authorNickname;

    @Schema(description = "작성 일시", example = "2026-04-29T10:30:00")
    private LocalDateTime createdAt;
}
