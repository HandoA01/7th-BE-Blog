package com.example.blog.exception;

public class DuplicateNicknameException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateNicknameException() {
        super(ErrorCode.DUPLICATE_NICKNAME.getMessage());
        this.errorCode = ErrorCode.DUPLICATE_NICKNAME;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
