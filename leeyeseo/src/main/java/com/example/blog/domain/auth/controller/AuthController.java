package com.example.blog.domain.auth.controller;

import com.example.blog.domain.auth.service.AuthService;
import com.example.blog.dto.auth.LoginRequest;
import com.example.blog.dto.auth.ReissueRequest;
import com.example.blog.dto.auth.SignupRequest;
import com.example.blog.dto.auth.TokenResponse;
import com.example.blog.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "회원가입/로그인 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /auth/signup - 회원가입
    @Operation(
            summary = "회원가입",
            description = "이메일/비밀번호/닉네임으로 회원가입을 진행합니다. " +
                    "이메일·닉네임 중복 검증을 거치며, 성공 시 accessToken과 refreshToken을 함께 발급합니다."
    )
    @PostMapping("/signup")
    public ApiResponse<TokenResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ApiResponse.onSuccess(authService.signup(request));
    }

    // POST /auth/login - 로그인
    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인합니다. 성공 시 accessToken과 refreshToken을 발급합니다."
    )
    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.onSuccess(authService.login(request));
    }

    // POST /auth/reissue - 토큰 재발급
    @Operation(
            summary = "토큰 재발급",
            description = "유효한 refreshToken으로 새로운 accessToken과 refreshToken을 발급받습니다. " +
                    "만료되거나 위조된 토큰은 401 응답을 반환합니다."
    )
    @PostMapping("/reissue")
    public ApiResponse<TokenResponse> reissue(@Valid @RequestBody ReissueRequest request) {
        return ApiResponse.onSuccess(authService.reissue(request));
    }
}
