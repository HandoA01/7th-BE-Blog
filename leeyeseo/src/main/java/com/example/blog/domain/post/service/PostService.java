package com.example.blog.domain.post.service;

import com.example.blog.domain.post.converter.PostConverter;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import com.example.blog.dto.post.PostCreateRequest;
import com.example.blog.dto.post.PostDetailResponse;
import com.example.blog.dto.post.PostSummaryResponse;
import com.example.blog.dto.post.PostUpdateRequest;
import com.example.blog.exception.PostForbiddenException;
import com.example.blog.exception.PostNotFoundException;
import com.example.blog.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getPosts() {
        return postRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(PostConverter::toSummaryResponse)
                .collect(Collectors.toList());
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long postId) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        return PostConverter.toDetailResponse(post);
    }

    // 게시글 작성
    @Transactional
    public PostDetailResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        Post post = PostConverter.toEntity(request, user);
        postRepository.save(post);
        return PostConverter.toDetailResponse(post);
    }

    // 게시글 수정
    @Transactional
    public PostDetailResponse updatePost(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        // 작성자 권한 확인
        if (!post.getUser().getId().equals(request.getUserId())) {
            throw new PostForbiddenException();
        }

        post.update(request.getTitle(), request.getContent(), request.getImageUrl());
        return PostConverter.toDetailResponse(post);
    }

    // 게시글 삭제 (soft delete)
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        // 작성자 권한 확인
        if (!post.getUser().getId().equals(userId)) {
            throw new PostForbiddenException();
        }

        post.delete(); // BaseEntity의 soft delete
    }
}
