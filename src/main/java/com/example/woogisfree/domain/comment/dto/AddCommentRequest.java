package com.example.woogisfree.domain.comment.dto;

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
}
