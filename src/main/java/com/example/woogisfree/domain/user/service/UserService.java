package com.example.woogisfree.domain.user.service;

import com.example.woogisfree.domain.user.dto.AddUserRequest;
import com.example.woogisfree.domain.user.dto.SignUpRequest;
import com.example.woogisfree.domain.user.dto.UserResponse;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.global.security.JwtToken;

import java.util.Optional;

public interface UserService {

    JwtToken signIn(String username, String password);

    UserResponse signUp(SignUpRequest signUpRequest);

    ApplicationUser findUserById(Long id);

    Optional<ApplicationUser> findUserByUsername(String username);
}
