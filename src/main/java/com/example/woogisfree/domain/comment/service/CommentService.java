package com.example.woogisfree.domain.comment.service;

import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
import com.example.woogisfree.domain.comment.dto.AddCommentResponse;
import com.example.woogisfree.domain.comment.dto.CommentResponse;
import com.example.woogisfree.domain.comment.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    /**
     * 전체 조회 (req : articleId / res : 내용, 아이디, 작성일, 편집여부)
     * 생성 (req : 내용 / res : 아이디, 내용, 작성일, 편집여부)
     * 수정 (req : commentId, 내용 / res : 아이디, 내용, 작성일, 편집여부)
     * 삭제 (req : commentId)
     */
    AddCommentResponse save(AddCommentRequest request);

    Map<Long, List<CommentResponse>> findAllByArticleIds(List<Long> articleIds);
    void update(long commentId, String content);
    void delete(long commentId);
}