package com.example.blog.exception;

import lombok.Getter;

@Getter
public class AlreadyResolvedException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyResolvedException() {
        super(ErrorCode.ALREADY_RESOLVED.getMessage());
        this.errorCode = ErrorCode.ALREADY_RESOLVED;
    }

}
