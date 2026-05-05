package com.example.blog.exception;

public class CommentForbiddenException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommentForbiddenException() {
        super(ErrorCode.NOT_COMMENT_OWNER.getMessage());
        this.errorCode = ErrorCode.NOT_COMMENT_OWNER;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
