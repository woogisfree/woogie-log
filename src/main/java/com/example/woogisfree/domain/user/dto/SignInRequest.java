package com.example.woogisfree.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인
 */
@Getter
@Setter
public class SignInRequest {
    private String username;
    private String password;
}
