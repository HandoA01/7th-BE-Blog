package com.example.blog.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "댓글 작성 요청 DTO")
public class CommentCreateRequest {

    @Schema(description = "댓글 내용", example = "좋은 글이네요!", maxLength = 500)
    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 500, message = "댓글은 최대 500자까지 입력 가능합니다.")
    private String content;
}
