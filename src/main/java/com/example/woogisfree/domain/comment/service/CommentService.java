package com.example.woogisfree.domain.comment.service;

import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
import com.example.woogisfree.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    /**
     * 전체 조회 (req : articleId / res : 내용, 아이디, 작성일, 편집여부)
     * 생성 (req : 내용 / res : 아이디, 내용, 작성일, 편집여부)
     * 수정 (req : commentId, 내용 / res : 아이디, 내용, 작성일, 편집여부)
     * 삭제 (req : commentId)
     */
    Comment save(AddCommentRequest request);
    List<Comment> findAll(long articleId);
    void update(long commentId, String content);
    void delete(long commentId);
}