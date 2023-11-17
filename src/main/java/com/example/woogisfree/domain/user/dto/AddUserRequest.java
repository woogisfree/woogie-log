package com.example.woogisfree.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {

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
}
