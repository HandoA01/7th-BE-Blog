package com.example.blog.domain.auth.controller;

import com.example.blog.domain.auth.service.AuthService;
import com.example.blog.dto.auth.LoginRequest;
import com.example.blog.dto.auth.ReissueRequest;
import com.example.blog.dto.auth.SignupRequest;
import com.example.blog.dto.auth.TokenResponse;
import com.example.blog.dto.auth.kakao.KakaoLoginRequest;
import com.example.blog.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Tag(name = "Auth", description = "회원가입/로그인 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.authorization-uri}")
    private String kakaoAuthorizationUri;

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

    // GET /auth/kakao - 카카오 로그인 URL 발급 (편의용)
    @Operation(
            summary = "카카오 로그인 URL 발급",
            description = "사용자를 카카오 로그인 페이지로 보낼 URL을 반환합니다. " +
                    "프론트엔드는 이 URL로 사용자를 리다이렉트하고, " +
                    "사용자가 동의하면 카카오가 redirect_uri로 인가 코드를 전달합니다."
    )
    @GetMapping("/kakao")
    public ApiResponse<String> getKakaoLoginUrl() {
        String encodedRedirectUri = URLEncoder.encode(kakaoRedirectUri, StandardCharsets.UTF_8);
        String url = kakaoAuthorizationUri
                + "?response_type=code"
                + "&client_id=" + kakaoClientId
                + "&redirect_uri=" + encodedRedirectUri;
        return ApiResponse.onSuccess(url);
    }

    // POST /auth/kakao/login - 카카오 로그인
    @Operation(
            summary = "카카오 로그인",
            description = "카카오에서 발급받은 인가 코드로 우리 서비스의 accessToken과 refreshToken을 발급받습니다. " +
                    "최초 로그인 시 자동으로 회원가입까지 처리됩니다."
    )
    @PostMapping("/kakao/login")
    public ApiResponse<TokenResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest request) {
        return ApiResponse.onSuccess(authService.kakaoLogin(request.getCode()));
    }
}
