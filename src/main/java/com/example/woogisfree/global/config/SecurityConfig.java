package com.example.woogisfree.global.config;

import com.example.woogisfree.domain.user.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final UserDetailsService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring()
                .requestMatchers(toString())
                .requestMatchers("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/articles/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true))
                .rememberMe(Customizer.withDefaults()).build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
