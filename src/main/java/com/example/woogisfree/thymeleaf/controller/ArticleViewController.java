package com.example.woogisfree.thymeleaf.controller;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.service.ArticleService;
import com.example.woogisfree.thymeleaf.dto.ArticleListViewResponse;
import com.example.woogisfree.thymeleaf.dto.ArticleViewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleViewController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = articleService.findAll().stream()
                .sorted(Comparator.comparing(Article::getId))
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable long id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {

        log.info("id={}", id);

        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = articleService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
