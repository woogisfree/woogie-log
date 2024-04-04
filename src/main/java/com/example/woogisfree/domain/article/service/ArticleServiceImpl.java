package com.example.woogisfree.domain.article.service;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.ArticleResponse;
import com.example.woogisfree.domain.article.dto.UpdateArticleRequest;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.exception.ArticleNotFoundException;
import com.example.woogisfree.domain.article.repository.ArticleRepository;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + id));
    }

    @Override
    @Transactional
    public void delete(long articleId) {
        articleRepository.deleteById(articleId);
    }


    //TODO ifpresentorelse 추가
    @Override
    @Transactional
    public ArticleResponse update(long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + id));
        article.update(request.getTitle(), request.getContent());
        return new ArticleResponse(article);
    }
}
