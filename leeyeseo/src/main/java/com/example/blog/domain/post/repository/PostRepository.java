package com.example.blog.domain.post.repository;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.entity.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByDeletedAtIsNullAndStatus(PostStatus status);

    Optional<Post> findByIdAndDeletedAtIsNull(Long id);
}
