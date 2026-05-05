package com.example.blog.exception;

import lombok.Getter;

@Getter
public class ReportNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ReportNotFoundException(Long reportId) {
        super(ErrorCode.REPORT_NOT_FOUND.getMessage() + " id: " + reportId);
        this.errorCode = ErrorCode.REPORT_NOT_FOUND;
    }

}
