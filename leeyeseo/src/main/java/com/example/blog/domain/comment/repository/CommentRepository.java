package com.example.blog.domain.comment.repository;

import com.example.blog.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 게시글의 댓글 목록 조회 (삭제되지 않은 것만, 작성 시간 오름차순)
    List<Comment> findAllByPostIdAndDeletedAtIsNullOrderByCreatedAtAsc(Long postId);

    // 댓글 단건 조회 (삭제되지 않은 것만)
    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);
}
