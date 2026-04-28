package com.example.blog.exception;

import lombok.Getter;

@Getter
public class DuplicateReportException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateReportException() {
        super(ErrorCode.DUPLICATE_REPORT.getMessage());
        this.errorCode = ErrorCode.DUPLICATE_REPORT;
    }

}
