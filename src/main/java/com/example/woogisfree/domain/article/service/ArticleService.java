package com.example.woogisfree.domain.article.service;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.UpdateArticleRequest;
import com.example.woogisfree.domain.article.entity.Article;

import java.util.List;

public interface ArticleService {

    Article save(AddArticleRequest request);

    List<Article> findAll();

    Article findById(long id);

    void delete(long id);

    Article update(long id, UpdateArticleRequest request);
}
