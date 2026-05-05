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
@Schema(description = "게시글 상세 조회 응답 DTO")
public class PostDetailResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long id;

    @Schema(description = "게시글 제목", example = "첫 게시글입니다")
    private String title;

    @Schema(description = "게시글 본문", example = "안녕하세요, 반갑습니다!")
    private String content;

    @Schema(description = "이미지 URL", example = "https://example.com/image.png")
    private String imageUrl;

    @Schema(description = "작성자 닉네임", example = "yeseo")
    private String authorNickname;

    @Schema(description = "작성 일시", example = "2026-04-29T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정 일시", example = "2026-04-29T11:00:00")
    private LocalDateTime updatedAt;
}
