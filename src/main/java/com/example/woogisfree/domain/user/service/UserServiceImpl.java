package com.example.woogisfree.domain.user.service;

import com.example.woogisfree.domain.user.dto.AddUserRequest;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.exception.EmailAlreadyExistsException;
import com.example.woogisfree.domain.user.exception.PasswordMismatchException;
import com.example.woogisfree.domain.user.exception.UserNotFoundException;
import com.example.woogisfree.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApplicationUser createUser(AddUserRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("Password and confirmation password do not match.");
        }

        //TODO Let's think about how to catch this part later
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        return userRepository.save(ApplicationUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
    }

    @Override
    public ApplicationUser findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot Find User. userId : " + userId));
    }

    @Override
    public ApplicationUser findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
