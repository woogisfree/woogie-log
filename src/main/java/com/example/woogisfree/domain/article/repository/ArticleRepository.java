package com.example.woogisfree.domain.article.repository;

import com.example.woogisfree.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
