package com.example.woogisfree.domain.article.dto;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddArticleRequest {

    private String title;
    private String content;
    private Long userId;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .user(new ApplicationUser(userId))
                .build();
    }
}
