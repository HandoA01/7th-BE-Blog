package com.example.blog.domain.user.entity;

import com.example.blog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Comment("이메일 (로그인 식별자)")
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    @Comment("비밀번호")
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    @Comment("이름")
    private String name;

    @Column(name = "nickname", nullable = false, length = 50)
    @Comment("닉네임")
    private String nickname;

    @Column(name = "phone_number", length = 20)
    @Comment("전화번호")
    private String phoneNumber;

    @Column(name = "profile_image", length = 1000)
    @Comment("프로필 이미지 URL")
    private String profileImage;
}
