//package com.example.woogisfree.domain.comment.service;
//
//import com.example.woogisfree.domain.article.entity.Article;
//import com.example.woogisfree.domain.article.repository.ArticleRepository;
//import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
//import com.example.woogisfree.domain.comment.dto.UpdateCommentRequest;
//import com.example.woogisfree.domain.comment.entity.Comment;
//import com.example.woogisfree.domain.comment.repository.CommentRepository;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class CommentServiceImpl implements CommentService {
//
//    private final ArticleRepository articleRepository;
//    private final CommentRepository commentRepository;
//
//    @Override
//    public Comment addComment(long articleId, AddCommentRequest request) {
//        Article article = articleRepository.findById(articleId)
//                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID=" + articleId));
//
//        Comment comment = Comment.builder()
//                .content(request.getContent())
//                .article(article)
//                .build();
//
//        return commentRepository.save(comment);
//    }
//
//    @Override
//    public Comment updateComment(long commentId, UpdateCommentRequest request) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new EntityNotFoundException("Comment not found with Id=" + commentId));
//        comment.update(request.getContent());
//
//        return commentRepository.save(comment);
//    }
//
//    @Override
//    public void deleteComment(long commentId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new EntityNotFoundException("Comment not found with Id=" + commentId));
//        commentRepository.delete(comment);
//    }
//}
