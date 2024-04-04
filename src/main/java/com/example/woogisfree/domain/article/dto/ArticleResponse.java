package com.example.woogisfree.domain.article.dto;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.comment.entity.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleResponse {

    private final String title;
    private final String content;
    private final List<Comment> commentList;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.commentList = article.getCommentList();
    }
}
