package com.example.woogisfree.domain.article.repository;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.comment.entity.Comment;
import com.example.woogisfree.domain.comment.repository.CommentRepository;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import com.example.woogisfree.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest(properties = {
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
        ApplicationUser userA = new ApplicationUser("firstnameA", "lastnameA", "usernameA", "email@emailA", "passwordA", UserRole.USER);
        ApplicationUser userB = new ApplicationUser("firstnameB", "lastnameB", "usernameB", "email@emailB", "passwordB", UserRole.USER);
        userRepository.save(userA);
        userRepository.save(userB);

        Article article1 = new Article("title1", "content1", userA);
        Article article2 = new Article("title2", "content2", userB);

        // userA, article1
        Comment comment1 = new Comment("content1", userA, article1);
        Comment comment2 = new Comment("content2", userA, article1);
        Comment comment3 = new Comment("content3", userA, article1);
        Comment comment4 = new Comment("content4", userA, article1);

        // userB, article2
        Comment comment5 = new Comment("content5", userB, article2);
        Comment comment6 = new Comment("content6", userB, article2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        commentRepository.save(comment5);
        commentRepository.save(comment6);

        article1.

        articleRepository.save(article1);
        articleRepository.save(article2);
    }

    @Test
    void shouldFindAllArticlesWithComments() {
        List<Article> articles = articleRepository.findAll();
        assertEquals(2, articles.size());
        assertTrue(articles.stream().anyMatch(article -> article.getTitle().equals("title1")));
        assertTrue(articles.stream().anyMatch(article -> article.getTitle().equals("title2")));

        for (Article article : articles) {
            if (article.getTitle().equals("title1")) {
                assertEquals(4, article.getCommentList().size());
            } else if (article.getTitle().equals("title2")) {
                assertEquals(2, article.getCommentList().size());
            }
        }
    }
}