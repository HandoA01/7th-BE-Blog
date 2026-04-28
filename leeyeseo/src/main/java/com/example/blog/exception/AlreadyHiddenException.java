package com.example.blog.exception;

import lombok.Getter;

@Getter
public class AlreadyHiddenException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyHiddenException() {
        super(ErrorCode.ALREADY_HIDDEN.getMessage());
        this.errorCode = ErrorCode.ALREADY_HIDDEN;
    }

}
