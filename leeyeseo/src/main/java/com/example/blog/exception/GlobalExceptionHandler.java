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

    // 신고 없음 404
    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleReportNotFound(ReportNotFoundException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 중복 신고 409
    @ExceptionHandler(DuplicateReportException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateReport(DuplicateReportException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 이미 처리된 신고 409
    @ExceptionHandler(AlreadyResolvedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAlreadyResolved(AlreadyResolvedException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 이미 숨김 처리된 게시글 409
    @ExceptionHandler(AlreadyHiddenException.class)
    public ResponseEntity<ApiResponse<Void>> handleAlreadyHidden(AlreadyHiddenException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 댓글 없음 404
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCommentNotFound(CommentNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 댓글 권한 없음 403
    @ExceptionHandler(CommentForbiddenException.class)
    public ResponseEntity<ApiResponse<Void>> handleCommentForbidden(CommentForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 비밀번호 불일치 401
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPassword(InvalidPasswordException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 유효하지 않은 토큰 401
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidToken(InvalidTokenException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 만료된 토큰 401
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredToken(ExpiredTokenException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // Refresh Token 없음 401
    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRefreshTokenNotFound(RefreshTokenNotFoundException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 이메일 중복 409
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEmail(DuplicateEmailException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    // 닉네임 중복 409
    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateNickname(DuplicateNicknameException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
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
