package com.example.woogisfree.domain.user.dto;

import com.example.woogisfree.domain.user.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {
    private String username;
    private UserRole role;
    private long expiresIn;
    private String error;
}
