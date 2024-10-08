package com.example.woogisfree.domain.article.web;

import com.example.woogisfree.domain.article.dto.ArticleSummaryResponse;
import com.example.woogisfree.domain.article.dto.ArticleViewResponse;
import com.example.woogisfree.domain.article.dto.ArticleWithCommentResponse;
import com.example.woogisfree.domain.article.service.ArticleService;
import com.example.woogisfree.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleViewController {

    private final ArticleService articleService;
    private final UserUtil userUtil;

    /**
     * TODO 게시글의 수가 많아지는 경우 처리
     * 1. 페이징 처리
     * 2. 쿼리 최적화
     * 3. 캐싱
     * 4. DB 인덱싱
     * 5. 무한스크롤 적용
     */
    @GetMapping("/articles")
    public String getArticles(Model model, Principal principal) {
        List<ArticleSummaryResponse> articles = articleService.findAllByOrderByIdDesc();
        model.addAttribute("articles", articles);
        userUtil.addCurrentUserToModel(model, principal);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable long id, Model model, Principal principal) {
        ArticleWithCommentResponse article = articleService.findById(id);
        model.addAttribute("article", article);

        userUtil.addCurrentUserToModel(model, principal);
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