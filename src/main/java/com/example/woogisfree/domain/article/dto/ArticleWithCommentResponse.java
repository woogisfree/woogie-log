package com.example.woogisfree.domain.article.dto;

import com.example.woogisfree.domain.comment.dto.CommentResponse;
import com.example.woogisfree.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleWithCommentResponse {
    private Long articleId;
    private String title;
    private String content;
    private List<CommentResponse> comments;

    @Builder
    public ArticleWithCommentResponse(Long articleId, String title, String content, List<Comment> comments) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.comments = CommentResponse.listOf(comments);
    }
}
