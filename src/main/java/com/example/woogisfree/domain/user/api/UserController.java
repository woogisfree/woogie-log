package com.example.woogisfree.domain.user.api;

import com.example.woogisfree.domain.user.dto.SignInRequest;
import com.example.woogisfree.domain.user.dto.SignInResponse;
import com.example.woogisfree.domain.user.dto.SignUpRequest;
import com.example.woogisfree.domain.user.dto.UserResponse;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.entity.UserRole;
import com.example.woogisfree.domain.user.service.UserService;
import com.example.woogisfree.global.common.file.FileStorageService;
import com.example.woogisfree.global.config.redis.RedisService;
import com.example.woogisfree.global.security.JwtToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RedisService redisService;
    private final FileStorageService fileStorageService;

    @Value("${jwt.refresh-token-validity-in-milliseconds}")
    private long refreshTokenValidityInMilliseconds;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        try {
            String username = signInRequest.getUsername();
            String password = signInRequest.getPassword();
            JwtToken jwtToken = userService.signIn(username, password);
            String accessToken = jwtToken.getAccessToken();
            String refreshToken = jwtToken.getRefreshToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            // 초 단위
            refreshTokenCookie.setMaxAge((int) (refreshTokenValidityInMilliseconds / 1000));
            response.addCookie(refreshTokenCookie);

            redisService.save(username, refreshToken, refreshTokenValidityInMilliseconds);

            UserRole role = userService.findUserByUsername(username)
                    .map(user -> user.getRole())
                    .orElseThrow(() -> new UsernameNotFoundException("username not found"));

            long expiresIn = (jwtToken.getAccessTokenExpiresAt().getTime() - System.currentTimeMillis()) / 1000;
            SignInResponse signInResponse = SignInResponse.builder()
                    .username(username)
                    .role(role)
                    .expiresIn(expiresIn)
                    .build();
            return ResponseEntity.ok().headers(headers).body(signInResponse);

        } catch (BadCredentialsException e) {
            SignInResponse errorResponse = SignInResponse.builder()
                    .error("Unauthorized: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (AuthenticationException e) {
            SignInResponse errorResponse = SignInResponse.builder()
                    .error("Authentication failed: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
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

    @PostMapping(value = "/profile-image", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            ApplicationUser currentUser = userService.findUserByUsername(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String storedProfileImagePath = fileStorageService.storeProfileImage(file, currentUser);
            log.info("Stored profile image={}", storedProfileImagePath);

            return ResponseEntity.status(HttpStatus.CREATED).body(storedProfileImagePath);
        } catch (Exception e) {
            log.error("Error uploading profile image", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile image");
        }
    }

    @DeleteMapping("/profile-image")
    public ResponseEntity<String> deleteProfileImage(Principal principal) {
        try {
            ApplicationUser currentUser = userService.findUserByUsername(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            fileStorageService.deleteProfileImage(currentUser);
            return ResponseEntity.ok("Profile image deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting profile image", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete profile image");
        }
    }
}