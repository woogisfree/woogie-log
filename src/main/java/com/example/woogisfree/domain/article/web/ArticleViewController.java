package com.example.woogisfree.domain.article.web;

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

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleViewController {

    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/articles")
    public String getArticles(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<ArticleSummaryResponse> articles = articleService.findAllByOrderByIdDesc();
        model.addAttribute("articles", articles);

        if (userDetails != null) {
            ApplicationUser currentUser = userService.findUserById(userService.getUserIdFromUserDetails(userDetails));
            model.addAttribute("currentUser", currentUser);
        }
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        ArticleWithCommentResponse article = articleService.findById(id);
        model.addAttribute("article", article);

        if (userDetails != null) {
            ApplicationUser currentUser = userService.findUserById(userService.getUserIdFromUserDetails(userDetails));
            model.addAttribute("currentUser", currentUser.getUsername());
        }
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

//infinite scroll
//https://webdesign.tutsplus.com/how-to-implement-infinite-scrolling-with-javascript--cms-37055t