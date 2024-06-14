package com.example.woogisfree.domain.article.dto;

import com.example.woogisfree.domain.comment.dto.CommentResponse;
import com.example.woogisfree.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
public class ArticleWithCommentResponse {
    private Long articleId;
    private String title;
    private String content;
    private List<CommentResponse> comments;
    private LocalDateTime createdAt;
    private String createdBy;

    @Builder
    public ArticleWithCommentResponse(Long articleId, String title, String content, LocalDateTime createdAt, String createdBy, List<Comment> comments) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.comments = CommentResponse.listOf(comments);
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
