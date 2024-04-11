package com.example.woogisfree.domain.article.repository;

import com.example.woogisfree.domain.article.dto.ArticleWithCommentResponse;
import com.example.woogisfree.domain.article.entity.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @EntityGraph(attributePaths = {"commentList"})
    List<Article> findAll();

    @EntityGraph(attributePaths = {"commentList"})
    Optional<Article> findAllById(Long id);
}
