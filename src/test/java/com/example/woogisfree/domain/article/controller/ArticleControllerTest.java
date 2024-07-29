package com.example.woogisfree.domain.article.controller;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.ArticleResponse;
import com.example.woogisfree.domain.article.dto.UpdateArticleRequest;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.exception.ArticleNotFoundException;
import com.example.woogisfree.domain.article.repository.ArticleRepository;
import com.example.woogisfree.domain.article.service.ArticleServiceImpl;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import com.example.woogisfree.domain.user.repository.UserRepository;
import com.example.woogisfree.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleServiceImpl articleService;

    @MockBean
    private UserService userService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    private ApplicationUser user;
    private Article article;

    @BeforeEach
    void setup() {
        user = ApplicationUser.builder()
                .firstName("firstname")
                .lastName("lastname")
                .username("username")
                .email("email@email")
                .password("password")
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        article = Article.builder()
                .title("title")
                .content("content")
                .user(user)
                .build();
        articleRepository.save(article);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void addArticle() throws Exception {
        //given
        AddArticleRequest request = new AddArticleRequest("title", "content", 1L);
        when(userService.getUserIdFromUserDetails(any())).thenReturn(1L);
        when(articleService.save(any())).thenReturn(new ArticleResponse(article));

        //when
        mockMvc.perform(post("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void delete_exist_Article() throws Exception {
        //given
        Long findArticleId = article.getId();

        //when
        mockMvc.perform(delete("/api/v1/articles/" + findArticleId))
                //then
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void delete_not_exist_Article() throws Exception {
        //given
        long nonExistArticleId = 9999L;

        //when
        doThrow(new ArticleNotFoundException("Article not found")).when(articleService).delete(nonExistArticleId);

        //then
        mockMvc.perform(delete("/api/v1/articles/" + nonExistArticleId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void update_Article() throws Exception {
        //given
        UpdateArticleRequest request = new UpdateArticleRequest("updatedTitle", "updatedContent");
        Article updatedArticle = Article.builder()
                .title("updatedTitle")
                .content("updatedContent")
                .user(user)
                .build();

        when(articleService.update(anyLong(), any(UpdateArticleRequest.class)))
                .thenReturn(new ArticleResponse(updatedArticle));

        //when
        mockMvc.perform(patch("/api/v1/articles/" + article.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedArticle.getTitle()))
                .andExpect(jsonPath("$.content").value(updatedArticle.getContent()));
    }


}