package com.example.woogisfree.domain.article.controller;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.ArticleResponse;
import com.example.woogisfree.domain.article.dto.UpdateArticleRequest;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Tag(name = "Article API")
@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Article savedArticle = articleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {

        List<ArticleResponse> articles = articleService.findAll()
                .stream()
                .sorted(Comparator.comparing(Article::getId))
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
