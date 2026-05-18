package com.example.blog.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.REFRESH_TOKEN_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
