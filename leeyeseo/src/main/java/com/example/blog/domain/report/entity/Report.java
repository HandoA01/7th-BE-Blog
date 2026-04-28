package com.example.blog.domain.report.entity;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.user.entity.User;
import com.example.blog.exception.AlreadyResolvedException;
import com.example.blog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(
        name = "report",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_report_user_post",
                columnNames = {"user_id", "post_id"}
        )
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("신고한 사용자")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @Comment("신고된 게시글")
    private Post post;

    @Column(name = "reason", nullable = false, length = 500)
    @Comment("신고 사유")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    @Comment("신고 상태 (PENDING/RESOLVED)")
    private ReportStatus status = ReportStatus.PENDING;

    public void resolve() {
        if (this.status == ReportStatus.RESOLVED) {
            throw new AlreadyResolvedException();
        }
        this.status = ReportStatus.RESOLVED;
    }
}
