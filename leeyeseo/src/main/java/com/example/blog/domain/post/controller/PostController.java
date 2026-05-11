package com.example.blog.domain.post.controller;

import com.example.blog.domain.post.service.PostService;
import com.example.blog.dto.post.PostCreateRequest;
import com.example.blog.dto.post.PostDetailResponse;
import com.example.blog.dto.post.PostSummaryResponse;
import com.example.blog.dto.post.PostUpdateRequest;
import com.example.blog.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 API")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // GET /posts - 게시글 목록 조회
    @Operation(summary = "게시글 목록 조회", description = "활성 상태인 모든 게시글 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<PostSummaryResponse>> getPosts() {
        return ApiResponse.onSuccess(postService.getPosts());
    }

    // GET /posts/{postId} - 게시글 상세 조회
    @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세 정보를 조회합니다.")
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPost(@PathVariable Long postId) {
        return ApiResponse.onSuccess(postService.getPost(postId));
    }

    // POST /posts - 게시글 작성
    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다. 헤더의 X-USER-ID로 작성자를 식별합니다.")
    @PostMapping
    public ApiResponse<PostDetailResponse> createPost(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody PostCreateRequest request) {
        return ApiResponse.onSuccess(postService.createPost(userId, request));
    }

    // PATCH /posts/{postId} - 게시글 수정
    @Operation(summary = "게시글 수정", description = "게시글의 제목과 내용을 수정합니다. 작성자만 수정 가능합니다.")
    @PatchMapping("/{postId}")
    public ApiResponse<PostDetailResponse> updatePost(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request) {
        return ApiResponse.onSuccess(postService.updatePost(userId, postId, request));
    }

    // DELETE /posts/{postId} - 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다 (soft delete). 작성자만 삭제 가능합니다.")
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long postId) {
        postService.deletePost(userId, postId);
        return ApiResponse.onSuccess();
    }

    // PATCH /posts/{postId}/hide - 게시글 숨김
    @Operation(
            summary = "게시글 숨김",
            description = "작성자가 자신의 게시글을 임시 비공개 상태(HIDDEN)로 전환합니다. " +
                    "관리자에 의한 부적절 콘텐츠 차단은 신고 처리 도메인에서 다룹니다."
    )
    @PatchMapping("/{postId}/hide")
    public ApiResponse<Void> hidePost(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long postId) {
        postService.hidePost(userId, postId);
        return ApiResponse.onSuccess();
    }
}
