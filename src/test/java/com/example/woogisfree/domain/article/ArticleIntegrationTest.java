package com.example.woogisfree.domain.article;

import com.example.woogisfree.domain.article.dto.AddArticleRequest;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import com.example.woogisfree.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ArticleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.save(
                ApplicationUser.builder()
                        .firstName("user")
                        .lastName("user")
                        .username("user")
                        .email("user@user.com")
                        .password("password")
                        .role(UserRole.USER)
                        .build()
        );
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void addArticle() throws Exception {
        //given
        AddArticleRequest request = new AddArticleRequest("title", "content", 1L);

        //when
        mockMvc.perform(post("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"));
    }
}
