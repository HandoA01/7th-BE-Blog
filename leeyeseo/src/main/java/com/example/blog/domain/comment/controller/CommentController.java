package com.example.blog.domain.comment.controller;

import com.example.blog.domain.comment.service.CommentService;
import com.example.blog.dto.comment.CommentCreateRequest;
import com.example.blog.dto.comment.CommentResponse;
import com.example.blog.dto.comment.CommentUpdateRequest;
import com.example.blog.global.response.ApiResponse;
import com.example.blog.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글 API")
@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // GET /posts/{postId}/comments - 댓글 목록 조회 (공개)
    @Operation(summary = "댓글 목록 조회", description = "특정 게시글에 달린 모든 댓글을 작성 시간 오름차순으로 조회합니다.")
    @GetMapping
    public ApiResponse<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ApiResponse.onSuccess(commentService.getComments(postId));
    }

    // POST /posts/{postId}/comments - 댓글 작성 (인증 필요)
    @Operation(summary = "댓글 작성", description = "특정 게시글에 댓글을 작성합니다. 인증된 사용자만 가능합니다.")
    @PostMapping
    public ApiResponse<CommentResponse> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest request) {
        return ApiResponse.onSuccess(commentService.createComment(userDetails.getId(), postId, request));
    }

    // PATCH /posts/{postId}/comments/{commentId} - 댓글 수정 (인증 필요)
    @Operation(summary = "댓글 수정", description = "특정 게시글의 댓글을 수정합니다. 작성자만 수정 가능합니다.")
    @PatchMapping("/{commentId}")
    public ApiResponse<CommentResponse> updateComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request) {
        return ApiResponse.onSuccess(commentService.updateComment(userDetails.getId(), postId, commentId, request));
    }

    // DELETE /posts/{postId}/comments/{commentId} - 댓글 삭제 (인증 필요)
    @Operation(summary = "댓글 삭제", description = "특정 게시글의 댓글을 삭제합니다 (soft delete). 작성자만 삭제 가능합니다.")
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        commentService.deleteComment(userDetails.getId(), postId, commentId);
        return ApiResponse.onSuccess();
    }
}
