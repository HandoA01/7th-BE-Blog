package com.example.blog.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400_000", "잘못된 요청입니다."),
    MISSING_REQUIRED_VALUE(HttpStatus.BAD_REQUEST, "400_001", "필수 입력값이 누락되었습니다."),
    TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "400_002", "제목은 최대 255자까지 입력 가능합니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "400_004", "데이터 타입이 올바르지 않습니다."),

    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "403_000", "접근 권한이 없습니다."),
    NOT_POST_OWNER(HttpStatus.FORBIDDEN, "403_001", "해당 게시글의 작성자가 아닙니다."),

    // 404
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "404_000", "존재하지 않는 API 경로입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "404_001", "요청하신 게시글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "404_002", "사용자를 찾을 수 없습니다."),

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

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
