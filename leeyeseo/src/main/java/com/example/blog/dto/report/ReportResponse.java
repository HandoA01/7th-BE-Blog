package com.example.blog.dto.report;

import com.example.blog.domain.report.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private Long id;
    private Long postId;
    private Long reporterId;
    private String reason;
    private ReportStatus status;
    private LocalDateTime createdAt;
}
