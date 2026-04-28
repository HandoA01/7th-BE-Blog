package com.example.blog.domain.report.repository;

import com.example.blog.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<Report> findByIdAndDeletedAtIsNull(Long id);
}
