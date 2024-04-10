package com.example.woogisfree.domain.article.dto;

import lombok.Getter;

@Getter
public class ArticleSummaryResponse {

    private Long id;
    private String title;
    private String content;
    private long commentCount;

    public ArticleSummaryResponse(Long id, String title, String content, long commentCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
    }
}
