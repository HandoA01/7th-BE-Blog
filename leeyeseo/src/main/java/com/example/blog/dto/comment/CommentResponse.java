package com.example.blog.dto.comment;

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
@Schema(description = "댓글 응답 DTO")
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "1")
    private Long id;

    @Schema(description = "댓글이 달린 게시글 ID", example = "5")
    private Long postId;

    @Schema(description = "작성자 ID", example = "10")
    private Long authorId;

    @Schema(description = "작성자 닉네임", example = "yeseo")
    private String authorNickname;

    @Schema(description = "댓글 내용", example = "좋은 글이네요!")
    private String content;

    @Schema(description = "작성 일시", example = "2026-04-29T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정 일시", example = "2026-04-29T11:00:00")
    private LocalDateTime updatedAt;
}
