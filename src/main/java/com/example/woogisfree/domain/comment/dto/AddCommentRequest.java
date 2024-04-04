package com.example.woogisfree.domain.comment.dto;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.comment.entity.Comment;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddCommentRequest {
    private String content;
    private Long userId;
    private Long articleId;

    public Comment toEntity(ApplicationUser user, Article article) {
        return Comment.builder()
                .content(content)
                .user(user)
                .article(article)
                .build();
    }
}
