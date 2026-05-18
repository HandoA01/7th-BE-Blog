package com.example.blog.exception;

public class InvalidLoginException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidLoginException() {
        super(ErrorCode.INVALID_LOGIN.getMessage());
        this.errorCode = ErrorCode.INVALID_LOGIN;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
