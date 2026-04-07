package com.example.blog.domain.post.entity;

import com.example.blog.domain.user.entity.User;
import com.example.blog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "post")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 255)
    @Comment("제목")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @Comment("본문")
    private String content;

    @Column(name = "image_url", length = 1000)
    @Comment("게시글 이미지 URL")
    private String imageUrl;

    public void update(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
