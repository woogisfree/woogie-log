package com.example.woogisfree.domain.comment.service;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.exception.ArticleNotFoundException;
import com.example.woogisfree.domain.article.repository.ArticleRepository;
import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
import com.example.woogisfree.domain.comment.dto.AddCommentResponse;
import com.example.woogisfree.domain.comment.entity.Comment;
import com.example.woogisfree.domain.comment.exception.CommentNotFoundException;
import com.example.woogisfree.domain.comment.repository.CommentRepository;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleRepository articleRepository;

    @Override
    public AddCommentResponse save(AddCommentRequest request) {
        ApplicationUser user = userService.findUserById(request.getUserId());
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + request.getArticleId()));
        Comment comment = request.toEntity(user, article);

        return new AddCommentResponse(comment.getContent(), user.getUsername(), article.getId());
    }

    @Override
    public void update(long commentId, String content) {
        commentRepository.findById(commentId)
                .ifPresentOrElse(comment -> comment.updateContent(content),
                        CommentNotFoundException::new);
    }

    @Override
    public void delete(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
    }
}

