package com.example.woogisfree.domain.article.repository;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.comment.entity.Comment;
import com.example.woogisfree.domain.comment.repository.CommentRepository;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import com.example.woogisfree.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
        (properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        }, showSql = false)
class ArticleRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        ApplicationUser user = new ApplicationUser("firstnameA", "lastnameA", "usernameA", "email@emailA", "passwordA", UserRole.USER);
        userRepository.save(user);

        Article article = new Article("title1", "content1", user);

        // userA, article1
        Comment comment1 = new Comment("content1", user, article);
        Comment comment2 = new Comment("content2", user, article);
        Comment comment3 = new Comment("content3", user, article);
        Comment comment4 = new Comment("content4", user, article);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);

        article.getCommentList().add(comment1);
        article.getCommentList().add(comment2);
        article.getCommentList().add(comment3);
        article.getCommentList().add(comment4);

        articleRepository.save(article);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldFindAllArticlesWithComments() {
        List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
        assertTrue(articles.stream().anyMatch(article -> article.getTitle().equals("title1")));

        for (Article article : articles) {
            if (article.getTitle().equals("title1")) {
                assertEquals(4, article.getCommentList().size());
            } else if (article.getTitle().equals("title2")) {
                assertEquals(2, article.getCommentList().size());
            }
        }
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldFindArticleByIdWithComments() {
        Long firstArticleId = articleRepository.findAll().get(0).getId();
        articleRepository.findAllById(firstArticleId).ifPresent(article -> {
            assertEquals("title1", article.getTitle());
            assertEquals(4, article.getCommentList().size());
        });
    }
}