package com.example.blog.exception;

public class CommentNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommentNotFoundException(Long commentId) {
        super(ErrorCode.COMMENT_NOT_FOUND.getMessage() + " id: " + commentId);
        this.errorCode = ErrorCode.COMMENT_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
