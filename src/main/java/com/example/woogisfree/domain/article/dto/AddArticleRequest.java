package com.example.woogisfree.domain.article.dto;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import jakarta.persistence.Temporal;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddArticleRequest {

    @NotBlank
    private String title;
    private String content;
    private Long userId;

    public Article toEntity(ApplicationUser user) {
        return Article.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
