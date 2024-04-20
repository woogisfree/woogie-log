package com.example.woogisfree.domain.article.controller;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.article.dto.ArticleResponse;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.exception.ArticleNotFoundException;
import com.example.woogisfree.domain.article.service.ArticleServiceImpl;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import com.example.woogisfree.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleServiceImpl articleService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void addArticle() throws Exception {
        //given
        AddArticleRequest request = new AddArticleRequest("title", "content", 1L);
        ApplicationUser user = new ApplicationUser("firstname", "lastname", "username", "email", "password", UserRole.USER);
        Article article = new Article("title", "content", user);

        when(userService.getUserIdFromUserDetails(any())).thenReturn(1L);
        when(articleService.save(any())).thenReturn(new ArticleResponse(article));

        //then
        mockMvc.perform(post("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void delete_exist_Article() throws Exception {
        //given
        ApplicationUser user = new ApplicationUser("firstname", "lastname", "username", "email", "password", UserRole.USER);
        Article article = new Article("title", "content", user);
        em.persist(article);

        //when
        Long findArticleId = article.getId();

        //then
        mockMvc.perform(delete("/api/v1/articles/" + findArticleId))
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
}