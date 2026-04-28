package com.example.blog.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    private ApiResponse(Boolean isSuccess, String code, String message, T result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    // 성공 응답 (데이터 있음)
    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, "2000", "OK", result);
    }

    // 성공 응답 (데이터 없음 - 삭제 등)
    public static ApiResponse<Void> onSuccess() {
        return new ApiResponse<>(true, "2000", "OK", null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> onFailure(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}
