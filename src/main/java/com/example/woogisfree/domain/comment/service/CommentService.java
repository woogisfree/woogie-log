package com.example.woogisfree.domain.comment.service;

import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
import com.example.woogisfree.domain.comment.dto.AddCommentResponse;

public interface CommentService {

    AddCommentResponse save(Long articleId, Long userId, AddCommentRequest request);

    void update(long commentId, String content);

    void delete(long commentId);
}