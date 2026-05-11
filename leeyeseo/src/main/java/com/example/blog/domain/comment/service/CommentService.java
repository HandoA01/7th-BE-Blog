package com.example.blog.domain.comment.service;

import com.example.blog.domain.comment.converter.CommentConverter;
import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.comment.repository.CommentRepository;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import com.example.blog.dto.comment.CommentCreateRequest;
import com.example.blog.dto.comment.CommentResponse;
import com.example.blog.dto.comment.CommentUpdateRequest;
import com.example.blog.exception.CommentForbiddenException;
import com.example.blog.exception.CommentNotFoundException;
import com.example.blog.exception.PostNotFoundException;
import com.example.blog.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 목록 조회 (특정 게시글)
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        // 게시글 존재 여부 확인
        postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return commentRepository.findAllByPostIdAndDeletedAtIsNullOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentConverter::toResponse)
                .collect(Collectors.toList());
    }

    // 댓글 작성
    @Transactional
    public CommentResponse createComment(Long userId, Long postId, CommentCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Comment comment = CommentConverter.toEntity(request, user, post);
        commentRepository.save(comment);
        return CommentConverter.toResponse(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponse updateComment(Long userId, Long postId, Long commentId, CommentUpdateRequest request) {
        // 게시글 존재 여부 확인
        postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Comment comment = commentRepository.findByIdAndDeletedAtIsNull(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getUser().getId().equals(userId)) {
            throw new CommentForbiddenException();
        }

        comment.update(request.getContent());
        return CommentConverter.toResponse(comment);
    }

    // 댓글 삭제 (soft delete)
    @Transactional
    public void deleteComment(Long userId, Long postId, Long commentId) {
        // 게시글 존재 여부 확인
        postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Comment comment = commentRepository.findByIdAndDeletedAtIsNull(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getUser().getId().equals(userId)) {
            throw new CommentForbiddenException();
        }

        comment.delete();
    }
}
