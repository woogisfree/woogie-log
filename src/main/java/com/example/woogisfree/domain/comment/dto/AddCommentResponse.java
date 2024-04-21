package com.example.woogisfree.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCommentResponse {
    private String content;
    private String username;
    private Long articleId;

    public AddCommentResponse(String content, String username, Long articleId) {
        this.content = content;
        this.username = username;
        this.articleId = articleId;
    }
}
