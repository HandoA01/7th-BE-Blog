package com.example.blog.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 255, message = "제목은 최대 255자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "본문은 필수입니다.")
    private String content;

    private String imageUrl;
}
