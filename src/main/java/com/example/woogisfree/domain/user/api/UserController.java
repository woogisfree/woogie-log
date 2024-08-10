package com.example.woogisfree.domain.user.api;

import com.example.woogisfree.domain.user.dto.SignInRequest;
import com.example.woogisfree.domain.user.dto.SignUpRequest;
import com.example.woogisfree.domain.user.dto.UserResponse;
import com.example.woogisfree.domain.user.service.UserService;
import com.example.woogisfree.global.security.JwtToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        try {
            String username = signInRequest.getUsername();
            String password = signInRequest.getPassword();
            JwtToken jwtToken = userService.signIn(username, password);
            log.info("request username = {}, password = {}", username, password);
            log.info("jwtToken accessToken = {}", jwtToken.getAccessToken());
            log.info("jwtToken refreshToken = {}", jwtToken.getRefreshToken());

            Cookie cookie = new Cookie("token", jwtToken.getAccessToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        UserResponse result = userService.signUp(signUpRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<Boolean> isLoggedIn(@AuthenticationPrincipal UserDetails userDetails) {
        boolean isLoggedIn = userDetails != null;
        return ResponseEntity.ok(isLoggedIn);
    }
}