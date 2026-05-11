package com.example.blog.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 수정 요청 DTO")
public class PostUpdateRequest {

    @Schema(description = "수정할 게시글 제목", example = "수정된 제목입니다", maxLength = 255)
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 255, message = "제목은 최대 255자까지 입력 가능합니다.")
    private String title;

    @Schema(description = "수정할 게시글 본문", example = "수정된 본문 내용입니다.")
    @NotBlank(message = "본문은 필수입니다.")
    private String content;

    @Schema(description = "수정할 이미지 URL (선택)", example = "https://example.com/new-image.png")
    private String imageUrl;
}
