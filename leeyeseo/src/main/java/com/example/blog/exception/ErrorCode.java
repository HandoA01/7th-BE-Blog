package com.example.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400_000", "잘못된 요청입니다."),
    MISSING_REQUIRED_VALUE(HttpStatus.BAD_REQUEST, "400_001", "필수 입력값이 누락되었습니다."),
    TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "400_002", "제목은 최대 255자까지 입력 가능합니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "400_004", "데이터 타입이 올바르지 않습니다."),
    INVALID_STATE_TRANSITION(HttpStatus.BAD_REQUEST, "400_005", "유효하지 않은 상태 전이입니다."),

    // 401 (인증 실패)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401_000", "인증이 필요합니다."),
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, "401_005", "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "401_001", "비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "401_002", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "401_003", "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "401_004", "저장된 Refresh Token을 찾을 수 없습니다."),

    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "403_000", "접근 권한이 없습니다."),
    NOT_POST_OWNER(HttpStatus.FORBIDDEN, "403_001", "해당 게시글의 작성자가 아닙니다."),
    NOT_COMMENT_OWNER(HttpStatus.FORBIDDEN, "403_002", "해당 댓글의 작성자가 아닙니다."),

    // 404
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "404_000", "존재하지 않는 API 경로입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "404_001", "요청하신 게시글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "404_002", "사용자를 찾을 수 없습니다."),
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "404_003", "요청하신 신고를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "404_004", "요청하신 댓글을 찾을 수 없습니다."),

    // 409 (중복/충돌)
    DUPLICATE_REPORT(HttpStatus.CONFLICT, "409_001", "이미 신고한 게시글입니다."),
    ALREADY_RESOLVED(HttpStatus.CONFLICT, "409_002", "이미 처리 완료된 신고입니다."),
    ALREADY_HIDDEN(HttpStatus.CONFLICT, "409_003", "이미 숨김 처리된 게시글입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "409_004", "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "409_005", "이미 사용 중인 닉네임입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500_000", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
