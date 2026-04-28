package com.example.blog.domain.post.controller;

import com.example.blog.domain.post.service.PostService;
import com.example.blog.dto.post.PostCreateRequest;
import com.example.blog.dto.post.PostDetailResponse;
import com.example.blog.dto.post.PostSummaryResponse;
import com.example.blog.dto.post.PostUpdateRequest;
import com.example.blog.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // GET /posts - 게시글 목록 조회
    @GetMapping
    public ApiResponse<List<PostSummaryResponse>> getPosts() {
        return ApiResponse.onSuccess(postService.getPosts());
    }

    // GET /posts/{postId} - 게시글 상세 조회
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPost(@PathVariable Long postId) {
        return ApiResponse.onSuccess(postService.getPost(postId));
    }

    // POST /posts - 게시글 작성
    @PostMapping
    public ApiResponse<PostDetailResponse> createPost(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody PostCreateRequest request) {
        return ApiResponse.onSuccess(postService.createPost(userId, request));
    }

    // PATCH /posts/{postId} - 게시글 수정
    @PatchMapping("/{postId}")
    public ApiResponse<PostDetailResponse> updatePost(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request) {
        return ApiResponse.onSuccess(postService.updatePost(userId, postId, request));
    }

    // DELETE /posts/{postId} - 게시글 삭제
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long postId) {
        postService.deletePost(userId, postId);
        return ApiResponse.onSuccess();
    }
}
