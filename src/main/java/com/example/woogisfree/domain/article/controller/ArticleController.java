package com.example.woogisfree.domain.article.controller;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.ArticleAllResponse;
import com.example.woogisfree.domain.article.dto.ArticleResponse;
import com.example.woogisfree.domain.article.dto.UpdateArticleRequest;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.service.ArticleService;
import com.example.woogisfree.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Tag(name = "Article", description = "게시글 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ArticleResponse> addArticle(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody AddArticleRequest request) {
        Long userId = getUserIdFromUserDetails(userDetails);
        request.setUserId(userId);
        ArticleResponse result = articleService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * TODO 게시글 전체 조회시에는 게시글 제목, 내용, 댓글 수만 조회하면 됨. 수정 필요
     */
    @GetMapping
    public ResponseEntity<List<ArticleAllResponse>> findAllArticles() {

        List<ArticleAllResponse> result = articleService.findAll()
                .stream()
                .sorted(Comparator.comparing(ArticleAllResponse::getArticleId).reversed())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") long id) {

        Article article = articleService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {

        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable("id") long id,
            @RequestBody UpdateArticleRequest request) {

        ArticleResponse updatedArticle = articleService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) userDetails;
            return userService.findUserByUsername(user.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("user not found")).getId();
        }
        return null;
    }
}
