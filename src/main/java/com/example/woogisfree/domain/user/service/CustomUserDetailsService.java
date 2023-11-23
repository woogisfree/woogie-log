package com.example.woogisfree.domain.user.service;

import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser applicationUser = userRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException("No user with " + username + " exists in the system");
        }

        return User.withUsername(applicationUser.getUsername())
                .password(applicationUser.getPassword())
                .roles("USER")
                .disabled(false)
                .build();
    }
}
