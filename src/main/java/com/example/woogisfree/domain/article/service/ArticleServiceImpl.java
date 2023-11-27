package com.example.woogisfree.domain.article.service;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.UpdateArticleRequest;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.exception.ArticleNotFoundException;
import com.example.woogisfree.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Article save(AddArticleRequest request) {
        return articleRepository.save(request.toEntity());
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + id));
    }

    @Override
    public void delete(long id) {
        articleRepository.deleteById(id);
    }


    //TODO ifpresentorelse 추가
    @Override
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + id));
        article.update(request.getTitle(), request.getContent());
        return article;
    }
}
