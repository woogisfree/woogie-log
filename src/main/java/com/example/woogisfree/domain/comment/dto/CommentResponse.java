package com.example.woogisfree.domain.comment.dto;

import com.example.woogisfree.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponse {
    private Long commentId;
    private String content;
    private LocalDateTime createdDate;
    private boolean isEdited;

    public CommentResponse(Long commentId, String content, LocalDateTime createdDate, boolean isEdited) {
        this.commentId = commentId;
        this.content = content;
        this.createdDate = createdDate;
        this.isEdited = isEdited;
    }

    public static List<CommentResponse> listOf(List<Comment> commentList) {
        return commentList.stream()
                .map(comment -> new CommentResponse(comment.getId(), comment.getContent(), comment.getCreatedAt(), comment.getIsEdited()))
                .toList();
    }
}
