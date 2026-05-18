package com.example.blog.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "토큰 재발급 요청 DTO")
public class ReissueRequest {

    @Schema(description = "Refresh Token", example = "eyJhbGciOiJIUzI1NiJ9.xxx.yyy")
    @NotBlank(message = "Refresh Token은 필수입니다.")
    private String refreshToken;
}
