package com.example.blog.domain.post.repository;

import com.example.blog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // soft delete: deletedAt이 null인 것만 조회
    List<Post> findAllByDeletedAtIsNull();

    Optional<Post> findByIdAndDeletedAtIsNull(Long id);
}
