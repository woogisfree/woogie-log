package com.example.woogisfree.global.config;

import com.example.woogisfree.domain.user.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final String[] PERMIT_URL_ARRAY = {
            "/login",
            "/login-error",
            "/register",

            //html, css, js
            "/webjars/**",
            "/css/*",
            "/static/**",
            "/templates/**",
    };

    //TODO: What is <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" /> ?
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PERMIT_URL_ARRAY).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").failureUrl("/login-error")
                        .defaultSuccessUrl("/articles", true)
                )
                .logout((logout -> {
                    logout.addLogoutHandler(new HeaderWriterLogoutHandler(
                                    new ClearSiteDataHeaderWriter(COOKIES, CACHE, STORAGE)))
                            .logoutSuccessUrl("/login");
                }))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
