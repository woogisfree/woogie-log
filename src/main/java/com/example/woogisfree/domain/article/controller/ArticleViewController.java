package com.example.woogisfree.domain.article.controller;

import com.example.woogisfree.domain.article.dto.ArticleSummaryResponse;
import com.example.woogisfree.domain.article.dto.ArticleViewResponse;
import com.example.woogisfree.domain.article.dto.ArticleWithCommentResponse;
import com.example.woogisfree.domain.article.service.ArticleService;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleViewController {

    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleSummaryResponse> articles = articleService.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
                .collect(Collectors.toList());
        model.addAttribute("articles", articles);
        return "articleList";
    }

    //TODO CommentList 를 반환할 수 있게 되었는데, 아래 커맨트 추가해서 잘 동작하는지 확인해야함
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        ArticleWithCommentResponse article = articleService.findById(id);
        model.addAttribute("article", article);

        ApplicationUser currentUser = userService.findUserById(userService.getUserIdFromUserDetails(userDetails));
        model.addAttribute("currentUser", currentUser);
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) model.addAttribute("article", new ArticleViewResponse());
        else {
            ArticleWithCommentResponse article = articleService.findById(id);
            model.addAttribute("article", article);
        }
        return "newArticle";
    }
}