package com.example.blog.exception;

public class InvalidPasswordException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD.getMessage());
        this.errorCode = ErrorCode.INVALID_PASSWORD;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
