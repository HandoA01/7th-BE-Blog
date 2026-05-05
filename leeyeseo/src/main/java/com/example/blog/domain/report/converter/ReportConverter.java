package com.example.blog.domain.report.converter;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.report.entity.Report;
import com.example.blog.domain.user.entity.User;
import com.example.blog.dto.report.ReportCreateRequest;
import com.example.blog.dto.report.ReportResponse;

public class ReportConverter {

    public static Report toEntity(ReportCreateRequest request, User user, Post post) {
        return Report.builder()
                .user(user)
                .post(post)
                .reason(request.getReason())
                .build();
    }

    public static ReportResponse toResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .postId(report.getPost().getId())
                .reporterId(report.getUser().getId())
                .reason(report.getReason())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
