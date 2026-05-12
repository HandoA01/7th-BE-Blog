package com.example.blog.domain.report.controller;

import com.example.blog.domain.report.service.ReportService;
import com.example.blog.dto.report.ReportCreateRequest;
import com.example.blog.dto.report.ReportResponse;
import com.example.blog.global.response.ApiResponse;
import com.example.blog.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "게시글 신고 API")
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // POST /posts/{postId}/reports - 게시물 신고 (인증 필요)
    @Operation(
            summary = "게시글 신고",
            description = "특정 게시글을 신고합니다. " +
                    "동일 사용자가 같은 게시글을 중복 신고할 수 없습니다."
    )
    @PostMapping("/posts/{postId}/reports")
    public ApiResponse<ReportResponse> createReport(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @Valid @RequestBody ReportCreateRequest request) {
        return ApiResponse.onSuccess(reportService.createReport(userDetails.getId(), postId, request));
    }

    // PATCH /reports/{reportId}/resolve - 신고 처리 완료 (인증 필요)
    @Operation(
            summary = "신고 처리 완료",
            description = "PENDING 상태인 신고를 RESOLVED 상태로 변경합니다. " +
                    "이미 처리된 신고는 다시 처리할 수 없습니다."
    )
    @PatchMapping("/reports/{reportId}/resolve")
    public ApiResponse<ReportResponse> resolveReport(
            @PathVariable Long reportId) {
        return ApiResponse.onSuccess(reportService.resolveReport(reportId));
    }
}
