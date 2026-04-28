package com.example.blog.domain.report.controller;

import com.example.blog.domain.report.service.ReportService;
import com.example.blog.dto.report.ReportCreateRequest;
import com.example.blog.dto.report.ReportResponse;
import com.example.blog.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // POST /posts/{postId}/reports - 게시물 신고
    @PostMapping("/posts/{postId}/reports")
    public ApiResponse<ReportResponse> createReport(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody ReportCreateRequest request) {
        return ApiResponse.onSuccess(reportService.createReport(userId, postId, request));
    }

    // PATCH /reports/{reportId}/resolve - 신고 처리 완료
    @PatchMapping("/reports/{reportId}/resolve")
    public ApiResponse<ReportResponse> resolveReport(
            @PathVariable Long reportId) {
        return ApiResponse.onSuccess(reportService.resolveReport(reportId));
    }
}
