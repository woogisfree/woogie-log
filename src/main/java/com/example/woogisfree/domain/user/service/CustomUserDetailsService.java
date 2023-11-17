package com.example.woogisfree.domain.user.service;

import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        ApplicationUser applicationUser = userRepository.findByEmail(email);
        if (applicationUser == null) {
            throw new UsernameNotFoundException("No user with " + email + " exists in the system");
        }

        return User.withUsername(email)
                .password(applicationUser.getPassword())
                .roles("USER")
                .disabled(false)
                .build();
    }
}
