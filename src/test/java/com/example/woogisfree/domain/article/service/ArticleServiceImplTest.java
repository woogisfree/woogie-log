package com.example.woogisfree.domain.article.service;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.ArticleResponse;
import com.example.woogisfree.domain.article.dto.ArticleSummaryResponse;
import com.example.woogisfree.domain.article.dto.ArticleWithCommentResponse;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.repository.ArticleRepository;
import com.example.woogisfree.domain.comment.entity.Comment;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class ArticleServiceImplTest {

    private final UserService userService = mock(UserService.class);
    private final ArticleRepository articleRepository = mock(ArticleRepository.class);
    private final ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository, userService);

    @Test
    void save() throws Exception {
        //given
        ApplicationUser user = new ApplicationUser("firstnameA", "lastnameA", "usernameA", "email@emailA", "passwordA", UserRole.USER);
        AddArticleRequest request = new AddArticleRequest("title", "content", user.getId());

        when(userService.findUserById(anyLong())).thenReturn(user);
        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        ArticleResponse response = articleService.save(request);

        //then
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getContent(), response.getContent());
    }

    @Test
    void findAll() throws Exception {
        //given
        ApplicationUser user = new ApplicationUser("firstnameA", "lastnameA", "usernameA", "email@emailA", "passwordA", UserRole.USER);
        Article article1 = new Article("title1", "content1", user);
        Article article2 = new Article("title2", "content2", user);
        List<Article> articles = Arrays.asList(article1, article2);

        when(articleRepository.findAll()).thenReturn(List.of(article1, article2));

        //when
        List<ArticleSummaryResponse> responses = articleService.findAll();

        //then
        assertEquals(articles.size(), responses.size());
        for (int i = 0; i < articles.size(); i++) {
            assertEquals(articles.get(i).getId(), responses.get(i).getId());
            assertEquals(articles.get(i).getTitle(), responses.get(i).getTitle());
            assertEquals(articles.get(i).getContent(), responses.get(i).getContent());
        }
    }

    @Test
    void findById() throws Exception {
        //given
        ApplicationUser user = new ApplicationUser("firstnameA", "lastnameA", "usernameA", "email@emailA", "passwordA", UserRole.USER);
        Article article = new Article("title1", "content1", user);
        Comment comment1 = new Comment("content1", user, article);
        Comment comment2 = new Comment("content2", user, article);
        article.getCommentList().add(comment1);
        article.getCommentList().add(comment2);

        when(articleRepository.findAllById(anyLong())).thenReturn(java.util.Optional.of(article));

        //when
        ArticleWithCommentResponse response = articleService.findById(1L);

        //then
        assertEquals(article.getId(), response.getArticleId());
        assertEquals(article.getTitle(), response.getTitle());
        assertEquals(article.getContent(), response.getContent());
        assertEquals(article.getCommentList().size(), response.getComments().size());
    }
}