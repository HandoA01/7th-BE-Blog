package com.example.blog.exception;

public class ExpiredTokenException extends RuntimeException {

    private final ErrorCode errorCode;

    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN.getMessage());
        this.errorCode = ErrorCode.EXPIRED_TOKEN;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
