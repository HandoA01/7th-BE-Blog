package com.example.blog.exception;

public class UserNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserNotFoundException(Long userId) {
        super(ErrorCode.USER_NOT_FOUND.getMessage() + " id: " + userId);
        this.errorCode = ErrorCode.USER_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
