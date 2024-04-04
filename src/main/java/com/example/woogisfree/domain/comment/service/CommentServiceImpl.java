package com.example.woogisfree.domain.comment.service;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.repository.ArticleRepository;
import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
import com.example.woogisfree.domain.comment.entity.Comment;
import com.example.woogisfree.domain.comment.exception.CommentNotFoundException;
import com.example.woogisfree.domain.comment.repository.CommentRepository;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public Comment save(AddCommentRequest request) {
        ApplicationUser user = userService.findUserById(request.getUserId());
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("article not found"));
        Comment comment = request.toEntity(user, article);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAll(long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("article not found"));
        return article.getCommentList();
    }

    @Override
    @Transactional
    public void update(long commentId, String content) {
        commentRepository.findById(commentId)
                .ifPresentOrElse(comment -> comment.updateContent(content),
                        CommentNotFoundException::new);
    }

    @Override
    @Transactional
    public void delete(long commentId) {
        commentRepository.deleteById(commentId);
    }
}

