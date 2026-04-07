package com.example.blog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidStringException.class)
    public ResponseEntity<String> handleInvalidString(InvalidStringException e) {
        // 400 상태 코드와 함께 에러 메시지를 반환합니다.
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
