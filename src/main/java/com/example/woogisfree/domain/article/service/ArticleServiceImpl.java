package com.example.woogisfree.domain.article.service;

import com.example.woogisfree.domain.article.dto.*;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.exception.ArticleNotFoundException;
import com.example.woogisfree.domain.article.repository.ArticleRepository;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Override
    @Transactional
    public ArticleResponse save(AddArticleRequest request) {
        ApplicationUser user = userService.findUserById(request.getUserId());
        Article savedArticle = articleRepository.save(request.toEntity(user));
        return new ArticleResponse(savedArticle);
    }

    @Override
    public List<ArticleSummaryResponse> findAll() {
        return articleRepository.findAll().stream()
                .map(article -> new ArticleSummaryResponse(article.getId(), article.getTitle(), article.getContent(), article.getCommentList().size()))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleWithCommentResponse findById(long id) {
        Article article = articleRepository.findAllById(id)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + id));

        return ArticleWithCommentResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .createdBy(article.getCreatedBy())
                .comments(article.getCommentList().stream()
                        .sorted(Comparator.comparing(comment -> comment.getCreatedAt(), Comparator.nullsLast(Comparator.naturalOrder())))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public void delete(long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + articleId));
        articleRepository.deleteById(article.getId());
    }

    @Override
    @Transactional
    public ArticleResponse update(long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + id));
        article.updateArticle(request.getTitle(), request.getContent());
        return new ArticleResponse(article);
    }
}
