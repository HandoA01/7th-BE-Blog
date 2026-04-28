package com.example.blog.exception;

public class PostForbiddenException extends RuntimeException {

    private final ErrorCode errorCode;

    public PostForbiddenException() {
        super(ErrorCode.NOT_POST_OWNER.getMessage());
        this.errorCode = ErrorCode.NOT_POST_OWNER;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
