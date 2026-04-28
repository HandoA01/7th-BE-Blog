package com.example.blog.exception;

public class PostNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public PostNotFoundException(Long postId) {
        super(ErrorCode.POST_NOT_FOUND.getMessage() + " id: " + postId);
        this.errorCode = ErrorCode.POST_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
