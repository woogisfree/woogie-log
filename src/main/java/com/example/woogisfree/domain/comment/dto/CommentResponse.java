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
    private String createdBy;
    private boolean isEdited;

    public CommentResponse(Long commentId, String content, LocalDateTime createdDate, String createdBy, boolean isEdited) {
        this.commentId = commentId;
        this.content = content;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.isEdited = isEdited;
    }

    public static List<CommentResponse> listOf(List<Comment> comments) {
        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getCreatedBy(),
                        comment.getIsEdited() != null ? comment.getIsEdited() : false
                ))
                .toList();
    }
}
