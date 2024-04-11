package com.example.woogisfree.domain.article.service;

import com.example.woogisfree.domain.article.dto.*;
import com.example.woogisfree.domain.article.entity.Article;

import java.util.List;

public interface ArticleService {

    ArticleResponse save(AddArticleRequest request);

    List<ArticleSummaryResponse> findAll();

    ArticleWithCommentResponse findById(long id);

    void delete(long articleId);

    ArticleResponse update(long id, UpdateArticleRequest request);
}
