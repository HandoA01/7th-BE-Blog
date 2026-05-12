package com.example.blog.exception;

public class DuplicateEmailException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL.getMessage());
        this.errorCode = ErrorCode.DUPLICATE_EMAIL;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
