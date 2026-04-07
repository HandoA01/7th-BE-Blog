package com.example.blog.exception;

import com.example.blog.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 기존 예외 유지
    @ExceptionHandler(InvalidStringException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidString(InvalidStringException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.onFailure(
                        ErrorCode.BAD_REQUEST.getCode(),
                        e.getMessage()
                ));
    }

    // 게시글 없음 404
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePostNotFound(PostNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 사용자 없음 404
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 권한 없음 403
    @ExceptionHandler(PostForbiddenException.class)
    public ResponseEntity<ApiResponse<Void>> handlePostForbidden(PostForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // @Valid 검증 실패 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse(ErrorCode.MISSING_REQUIRED_VALUE.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.onFailure(
                        ErrorCode.MISSING_REQUIRED_VALUE.getCode(),
                        message
                ));
    }

    // PathVariable 타입 불일치 400 (예: /posts/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.onFailure(
                        ErrorCode.INVALID_TYPE_VALUE.getCode(),
                        ErrorCode.INVALID_TYPE_VALUE.getMessage()
                ));
    }

    // 서버 내부 오류 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.onFailure(
                        ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
                ));
    }
}
