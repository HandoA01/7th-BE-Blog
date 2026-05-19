package com.example.blog.dto.auth.kakao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "카카오 로그인 요청 DTO")
public class KakaoLoginRequest {

    @Schema(description = "카카오 인가 코드 (카카오 로그인 후 redirect URI로 전달받음)",
            example = "abc123def456")
    @NotBlank(message = "인가 코드는 필수입니다.")
    private String code;
}
