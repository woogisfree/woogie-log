package com.example.woogisfree.domain.user.service;

import com.example.woogisfree.domain.user.dto.SignUpRequest;
import com.example.woogisfree.domain.user.dto.UserResponse;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import com.example.woogisfree.domain.user.exception.EmailAlreadyExistsException;
import com.example.woogisfree.domain.user.exception.PasswordMismatchException;
import com.example.woogisfree.domain.user.exception.UserNotFoundException;
import com.example.woogisfree.domain.user.repository.UserRepository;
import com.example.woogisfree.global.security.JwtToken;
import com.example.woogisfree.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManagerBuilder authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtToken signIn(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.getObject().authenticate(authenticationToken);
        return tokenProvider.generateToken(authentication);
    }

    @Override
    public UserResponse signUp(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new PasswordMismatchException("Password and confirmation password do not match.");
        }

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        ApplicationUser savedUser = userRepository.save(signUpRequest.toEntity(encodedPassword, UserRole.USER));

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public ApplicationUser findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot Find User. userId : " + userId));
    }

    @Override
    public Optional<ApplicationUser> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
