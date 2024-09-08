package com.example.woogisfree.domain.user.dto;

import com.example.woogisfree.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInResponse {
    private String accessToken;
    private String username;
    private UserRole role;
    private long expiresIn;
    private String error;
}
