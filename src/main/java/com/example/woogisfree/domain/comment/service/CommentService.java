package com.example.woogisfree.domain.comment.service;

import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
import com.example.woogisfree.domain.comment.dto.UpdateCommentRequest;
import com.example.woogisfree.domain.comment.entity.Comment;

public interface CommentService {

    Comment addComment(long articleId, AddCommentRequest request);

    Comment updateComment(long commentId, UpdateCommentRequest request);

    void deleteComment(long commentId);
}
