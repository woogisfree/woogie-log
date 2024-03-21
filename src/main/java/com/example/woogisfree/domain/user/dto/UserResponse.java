package com.example.woogisfree.domain.user.dto;

import com.example.woogisfree.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private UserRole role;
}
