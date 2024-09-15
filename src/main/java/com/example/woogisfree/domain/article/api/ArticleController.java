package com.example.woogisfree.domain.article.api;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.ArticleResponse;
import com.example.woogisfree.domain.article.dto.UpdateArticleRequest;
import com.example.woogisfree.domain.article.service.ArticleService;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Tag(name = "Article", description = "게시글 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ArticleResponse> addArticle(Principal principal, @RequestBody AddArticleRequest request) {
        ApplicationUser user = userService.findUserByUsername(principal.getName()).get();
        request.setUserId(user.getId());
        ArticleResponse result = articleService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
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
}
