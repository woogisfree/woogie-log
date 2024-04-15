package com.example.woogisfree.domain.comment.entity;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "boolean default false")
    private Boolean isEdited = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private ApplicationUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public Comment(String content, ApplicationUser user, Article article) {
        this.content = content;
        this.article = article;
        this.user = user;
    }

    public void updateContent(String content) {
        this.content = content;
        this.isEdited = true;
    }
}
