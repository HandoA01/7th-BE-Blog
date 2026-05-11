package com.example.blog.dto.report;

import com.example.blog.domain.report.entity.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "신고 응답 DTO")
public class ReportResponse {

    @Schema(description = "신고 ID", example = "1")
    private Long id;

    @Schema(description = "신고된 게시글 ID", example = "5")
    private Long postId;

    @Schema(description = "신고자 ID", example = "10")
    private Long reporterId;

    @Schema(description = "신고 사유", example = "부적절한 홍보성 게시글입니다.")
    private String reason;

    @Schema(description = "신고 처리 상태 (PENDING: 대기, RESOLVED: 처리완료)", example = "PENDING")
    private ReportStatus status;

    @Schema(description = "신고 일시", example = "2026-04-29T10:30:00")
    private LocalDateTime createdAt;
}
