package com.example.blog.domain.auth.repository;

import com.example.blog.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // userId로 Refresh Token 조회 (한 사용자당 1개)
    Optional<RefreshToken> findByUserId(Long userId);

    // 토큰 값으로 조회 (재발급 요청 시 검증용)
    Optional<RefreshToken> findByToken(String token);

    // userId로 삭제 (로그아웃 시 사용)
    void deleteByUserId(Long userId);
}
