package com.example.blog.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "토큰 응답 DTO")
public class TokenResponse {

    @Schema(description = "Access Token (30분 유효)", example = "eyJhbGciOiJIUzI1NiJ9.xxx.yyy")
    private String accessToken;

    @Schema(description = "Refresh Token (14일 유효)", example = "eyJhbGciOiJIUzI1NiJ9.aaa.bbb")
    private String refreshToken;

    @Schema(description = "토큰 타입", example = "Bearer")
    @Builder.Default
    private String tokenType = "Bearer";
}
