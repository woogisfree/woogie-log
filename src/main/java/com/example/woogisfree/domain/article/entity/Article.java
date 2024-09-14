package com.example.woogisfree.domain.article.entity;

import com.example.woogisfree.domain.comment.entity.Comment;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private ApplicationUser user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Article(String title, String content, ApplicationUser user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updateArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }
}