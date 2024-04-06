package com.example.woogisfree.domain.article.dto;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.comment.dto.CommentResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleAllResponse {
    private Long articleId;
    private String title;
    private String content;
    private List<CommentResponse> commentList;

    public ArticleAllResponse(Article article, List<CommentResponse> commentResponses) {
        this.articleId = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.commentList = commentResponses;
    }
}
