package com.example.woogisfree.domain.article.dto;

import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddArticleRequest {

    @NotBlank
    private String title;
    private String content;
    private Long userId;

    @JsonCreator
    public AddArticleRequest(
            @JsonProperty("title") String title,
            @JsonProperty("content") String content,
            @JsonProperty("userId") Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Article toEntity(ApplicationUser user) {
        return Article.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
