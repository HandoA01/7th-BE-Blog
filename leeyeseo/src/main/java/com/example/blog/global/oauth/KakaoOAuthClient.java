package com.example.blog.global.oauth;

import com.example.blog.dto.auth.kakao.KakaoTokenResponse;
import com.example.blog.dto.auth.kakao.KakaoUserInfoResponse;
import com.example.blog.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class KakaoOAuthClient {

    private final RestTemplate restTemplate;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    public KakaoOAuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 1단계: 인가 코드로 카카오 액세스 토큰 발급
     * POST https://kauth.kakao.com/oauth/token
     */
    public KakaoTokenResponse getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(
                    tokenUri,
                    HttpMethod.POST,
                    request,
                    KakaoTokenResponse.class
            );

            KakaoTokenResponse tokenResponse = response.getBody();
            if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
                log.warn("카카오 토큰 응답이 비어있습니다.");
                throw new InvalidTokenException();
            }

            log.info("카카오 액세스 토큰 발급 성공");
            return tokenResponse;

        } catch (RestClientException e) {
            log.warn("카카오 토큰 발급 실패: {}", e.getMessage());
            throw new InvalidTokenException();
        }
    }

    /**
     * 2단계: 카카오 액세스 토큰으로 사용자 정보 조회
     * GET https://kapi.kakao.com/v2/user/me
     */
    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange(
                    userInfoUri,
                    HttpMethod.GET,
                    request,
                    KakaoUserInfoResponse.class
            );

            KakaoUserInfoResponse userInfo = response.getBody();
            if (userInfo == null || userInfo.getId() == null) {
                log.warn("카카오 사용자 정보 응답이 비어있습니다.");
                throw new InvalidTokenException();
            }

            log.info("카카오 사용자 정보 조회 성공: kakaoId={}", userInfo.getId());
            return userInfo;

        } catch (RestClientException e) {
            log.warn("카카오 사용자 정보 조회 실패: {}", e.getMessage());
            throw new InvalidTokenException();
        }
    }
}