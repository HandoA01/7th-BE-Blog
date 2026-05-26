package com.example.blog.dto.auth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 카카오 사용자 정보 조회 API 응답
 * GET https://kapi.kakao.com/v2/user/me
 */
@Getter
@NoArgsConstructor
@ToString
public class KakaoUserInfoResponse {

    @JsonProperty("id")
    private Long id;   // 카카오 회원번호 (고유 식별자)

    @JsonProperty("properties")
    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Properties {

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;

        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class KakaoAccount {

        @JsonProperty("profile")
        private Profile profile;

        @JsonProperty("email")
        private String email;   // 비즈 앱 전환 시에만 받을 수 있음

        @Getter
        @NoArgsConstructor
        @ToString
        public static class Profile {

            @JsonProperty("nickname")
            private String nickname;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }
}
