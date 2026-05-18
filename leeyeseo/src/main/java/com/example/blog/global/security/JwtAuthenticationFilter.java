package com.example.blog.global.security;

import com.example.blog.exception.ExpiredTokenException;
import com.example.blog.exception.InvalidTokenException;
import com.example.blog.global.response.ApiResponse;
import com.example.blog.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 2. 토큰이 있으면 검증 + 인증 정보 등록
        if (StringUtils.hasText(token)) {
            try {
                jwtUtil.validateToken(token);

                // 토큰에서 email 추출 → UserDetails 조회
                String email = jwtUtil.getEmail(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // SecurityContext에 인증 정보 등록
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("인증 정보 등록 완료: {}", email);

            } catch (ExpiredTokenException e) {
                log.warn("만료된 토큰: {}", request.getRequestURI());
                sendErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
                return;
            } catch (InvalidTokenException e) {
                log.warn("유효하지 않은 토큰: {}", request.getRequestURI());
                sendErrorResponse(response, ErrorCode.INVALID_TOKEN);
                return;
            }
        }

        // 3. 다음 필터로 진행 (토큰이 없어도 통과 - 인증 필요 여부는 SecurityConfig에서 결정)
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 "Bearer " 떼고 토큰만 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    // 필터 단에서 발생한 인증 예외는 GlobalExceptionHandler가 못 잡으니 직접 응답 작성
    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ApiResponse<Void> apiResponse = ApiResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage()
        );

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
