package com.example.woogisfree.domain.article.controller;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.ArticleResponse;
import com.example.woogisfree.domain.article.dto.UpdateArticleRequest;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Article API")
@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {

        Article savedArticle = articleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {

        List<ArticleResponse> articles = articleService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") long id) {

        Article article = articleService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {

        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable("id") long id,
            @RequestBody UpdateArticleRequest request) {

        Article updatedArticle = articleService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }
}
