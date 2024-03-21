package com.example.woogisfree.domain.user.dto;

import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입
 */
@Getter
@Setter
public class SignUpRequest {
    @NotEmpty(message = "Enter your firstname")
    private String firstName;

    @NotEmpty(message = "Enter your lastname")
    private String lastName;

    @NotEmpty(message = "Enter a username")
    private String username;

    @NotEmpty(message = "Enter an email")
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "Enter a password")
    private String password;

    @NotEmpty(message = "Confirm your password")
    private String confirmPassword;
    private UserRole role;

    public ApplicationUser toEntity(String encodedPassword, UserRole role) {
        return ApplicationUser.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .password(encodedPassword)
                .role(role)
                .build();
    }
}
