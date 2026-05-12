package com.example.blog.global.security;

import com.example.blog.exception.ExpiredTokenException;
import com.example.blog.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey key;

    // Bean 생성 후 secret을 SecretKey 객체로 변환
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(io.jsonwebtoken.io.Encoders.BASE64.encode(secret.getBytes()));
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Access Token 발급
    public String generateAccessToken(Long userId, String email) {
        return generateToken(userId, email, accessTokenExpiration);
    }

    // Refresh Token 발급
    public String generateRefreshToken(Long userId, String email) {
        return generateToken(userId, email, refreshTokenExpiration);
    }

    // 토큰 생성 (공통)
    private String generateToken(Long userId, String email, long expirationMillis) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + expirationMillis);

        return Jwts.builder()
                .subject(String.valueOf(userId))   // 토큰 주체 = userId
                .claim("email", email)              // 추가 정보 = email
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    // 토큰에서 userId 추출
    public Long getUserId(String token) {
        return Long.parseLong(parseClaims(token).getSubject());
    }

    // 토큰에서 email 추출
    public String getEmail(String token) {
        return parseClaims(token).get("email", String.class);
    }

    // 토큰 유효성 검증
    public void validateToken(String token) {
        try {
            parseClaims(token);
        } catch (ExpiredJwtException e) {
            log.warn("만료된 토큰: {}", e.getMessage());
            throw new ExpiredTokenException();
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("유효하지 않은 토큰: {}", e.getMessage());
            throw new InvalidTokenException();
        }
    }

    // 토큰 파싱 (Claims 추출)
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Refresh Token 만료 시간 (다른 클래스에서 참조용)
    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }
}
