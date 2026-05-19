package com.example.blog.domain.auth.service;

import com.example.blog.domain.auth.entity.RefreshToken;
import com.example.blog.domain.auth.repository.RefreshTokenRepository;
import com.example.blog.domain.user.entity.Provider;
import com.example.blog.domain.user.entity.Role;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import com.example.blog.dto.auth.LoginRequest;
import com.example.blog.dto.auth.ReissueRequest;
import com.example.blog.dto.auth.SignupRequest;
import com.example.blog.dto.auth.TokenResponse;
import com.example.blog.dto.auth.kakao.KakaoTokenResponse;
import com.example.blog.dto.auth.kakao.KakaoUserInfoResponse;
import com.example.blog.exception.*;
import com.example.blog.global.oauth.KakaoOAuthClient;
import com.example.blog.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final KakaoOAuthClient kakaoOAuthClient;

    // 회원가입
    @Transactional
    public TokenResponse signup(SignupRequest request) {
        // 1. 이메일/닉네임 중복 검증
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new DuplicateNicknameException();
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. User 생성 및 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .nickname(request.getNickname())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .build();
        userRepository.save(user);

        // 4. 토큰 발급 (자동 로그인)
        return issueTokens(user);
    }

    // 로그인
    @Transactional
    public TokenResponse login(LoginRequest request) {
        // 1. 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidLoginException::new);

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidLoginException();
        }

        // 3. 토큰 발급
        return issueTokens(user);
    }

    // 토큰 재발급
    @Transactional
    public TokenResponse reissue(ReissueRequest request) {
        // 1. 토큰 유효성 검증 (만료/위조 체크)
        jwtUtil.validateToken(request.getRefreshToken());

        // 2. DB에 저장된 Refresh Token인지 확인
        RefreshToken storedToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(RefreshTokenNotFoundException::new);

        // 3. 토큰에서 userId 추출 → 사용자 조회
        Long userId = jwtUtil.getUserId(request.getRefreshToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // 4. 새 토큰 발급 + DB의 Refresh Token 갱신 (회전)
        return issueTokens(user);
    }

    // 카카오 로그인 (신규)
    @Transactional
    public TokenResponse kakaoLogin(String code) {
        // 1. 인가 코드로 카카오 액세스 토큰 발급
        KakaoTokenResponse tokenResponse = kakaoOAuthClient.getAccessToken(code);

        // 2. 카카오 액세스 토큰으로 사용자 정보 조회
        KakaoUserInfoResponse userInfo = kakaoOAuthClient.getUserInfo(tokenResponse.getAccessToken());

        // 3. 기존 카카오 회원 조회, 없으면 자동 회원가입
        String providerId = String.valueOf(userInfo.getId());
        User user = userRepository.findByProviderAndProviderId(Provider.KAKAO, providerId)
                .orElseGet(() -> createKakaoUser(userInfo, providerId));

        // 4. 우리 서비스 JWT 발급
        return issueTokens(user);
    }

    // ===== private helper =====

    // 카카오 사용자 자동 회원가입
    private User createKakaoUser(KakaoUserInfoResponse userInfo, String providerId) {
        // 카카오는 이메일을 못 받으므로 가짜 이메일 생성
        String fakeEmail = "kakao_" + providerId + "@kakao.local";

        // 카카오 닉네임 추출 (kakao_account.profile 우선, 없으면 properties)
        String kakaoNickname = extractNickname(userInfo);

        // 닉네임 중복 시 카카오 ID 일부 붙여서 고유하게 변경
        String nickname = kakaoNickname;
        if (userRepository.existsByNickname(nickname)) {
            nickname = kakaoNickname + "_" + providerId;
        }

        // 카카오 사용자는 비밀번호 사용 안 하지만, 컬럼이 nullable=false라 랜덤 값으로 채움
        String randomPassword = passwordEncoder.encode(UUID.randomUUID().toString());

        // 카카오 프로필 이미지 추출
        String profileImage = extractProfileImage(userInfo);

        User newUser = User.builder()
                .email(fakeEmail)
                .password(randomPassword)
                .name(kakaoNickname)
                .nickname(nickname)
                .profileImage(profileImage)
                .role(Role.USER)
                .provider(Provider.KAKAO)
                .providerId(providerId)
                .build();

        return userRepository.save(newUser);
    }

    // 카카오 응답에서 닉네임 추출
    private String extractNickname(KakaoUserInfoResponse userInfo) {
        if (userInfo.getKakaoAccount() != null
                && userInfo.getKakaoAccount().getProfile() != null
                && userInfo.getKakaoAccount().getProfile().getNickname() != null) {
            return userInfo.getKakaoAccount().getProfile().getNickname();
        }
        if (userInfo.getProperties() != null
                && userInfo.getProperties().getNickname() != null) {
            return userInfo.getProperties().getNickname();
        }
        return "카카오사용자";
    }

    // 카카오 응답에서 프로필 이미지 URL 추출
    private String extractProfileImage(KakaoUserInfoResponse userInfo) {
        if (userInfo.getKakaoAccount() != null
                && userInfo.getKakaoAccount().getProfile() != null) {
            return userInfo.getKakaoAccount().getProfile().getProfileImageUrl();
        }
        if (userInfo.getProperties() != null) {
            return userInfo.getProperties().getProfileImage();
        }
        return null;
    }

    // 토큰 발급 + RefreshToken DB 저장 공통 로직
    private TokenResponse issueTokens(User user) {
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getEmail());

        // RefreshToken DB에 저장 또는 갱신 (한 사용자당 1개 유지)
        LocalDateTime expiresAt = LocalDateTime.now()
                .plusNanos(jwtUtil.getRefreshTokenExpiration() * 1_000_000);

        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        existing -> existing.update(refreshToken, expiresAt),
                        () -> refreshTokenRepository.save(
                                RefreshToken.builder()
                                        .userId(user.getId())
                                        .token(refreshToken)
                                        .expiresAt(expiresAt)
                                        .build()
                        )
                );

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
