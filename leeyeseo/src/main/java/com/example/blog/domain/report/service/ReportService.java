package com.example.blog.domain.report.service;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.report.converter.ReportConverter;
import com.example.blog.domain.report.entity.Report;
import com.example.blog.domain.report.repository.ReportRepository;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import com.example.blog.dto.report.ReportCreateRequest;
import com.example.blog.dto.report.ReportResponse;
import com.example.blog.exception.DuplicateReportException;
import com.example.blog.exception.PostNotFoundException;
import com.example.blog.exception.ReportNotFoundException;
import com.example.blog.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReportResponse createReport(Long userId, Long postId, ReportCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (reportRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new DuplicateReportException();
        }

        Report report = ReportConverter.toEntity(request, user, post);
        reportRepository.save(report);
        return ReportConverter.toResponse(report);
    }

    @Transactional
    public ReportResponse resolveReport(Long reportId) {
        Report report = reportRepository.findByIdAndDeletedAtIsNull(reportId)
                .orElseThrow(() -> new ReportNotFoundException(reportId));

        report.resolve();
        return ReportConverter.toResponse(report);
    }
}
