package com.example.blog.domain.auth.entity;

import com.example.blog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    @Comment("사용자 ID (한 사용자당 하나의 Refresh Token만 유지)")
    private Long userId;

    @Column(name = "token", nullable = false, length = 500)
    @Comment("Refresh Token 값")
    private String token;

    @Column(name = "expires_at", nullable = false)
    @Comment("토큰 만료 시각")
    private LocalDateTime expiresAt;

    // 토큰 갱신 (재발급 시 새 토큰으로 교체)
    public void update(String newToken, LocalDateTime newExpiresAt) {
        this.token = newToken;
        this.expiresAt = newExpiresAt;
    }

    // 만료 여부 확인
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
