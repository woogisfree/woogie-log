package com.example.woogisfree.domain.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private Long articleId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
