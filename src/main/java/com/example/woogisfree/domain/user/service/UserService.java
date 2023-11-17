package com.example.woogisfree.domain.user.service;

import com.example.woogisfree.domain.user.dto.AddUserRequest;
import com.example.woogisfree.domain.user.entity.ApplicationUser;

public interface UserService {

    ApplicationUser createUser(AddUserRequest request);
    ApplicationUser findByEmail(String email);
}
