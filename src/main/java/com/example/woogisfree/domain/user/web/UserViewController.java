package com.example.woogisfree.domain.user.web;

import com.example.woogisfree.domain.user.dto.SignUpRequest;
import com.example.woogisfree.domain.user.service.UserService;
import com.example.woogisfree.global.config.redis.RedisService;
import com.example.woogisfree.global.security.TokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("user", new SignUpRequest());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(
            @Valid @ModelAttribute("user") SignUpRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "sign-up";
        }
        userService.signUp(request);
        return "redirect:sign-up?success";
    }

    @GetMapping("/sign-in")
    public String signIn() {
        return "sign-in";
    }

    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut(HttpServletRequest request, HttpServletResponse response,
                                          @RequestHeader("Authorization") String accessToken
    ) {
        try {
            String parsedAccessToken = accessToken.replace("Bearer ", "");
            String username = tokenProvider.extractUsername(parsedAccessToken);
            redisService.delete(username);

            Cookie cookie = new Cookie("refreshToken", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            log.info("SIGN OUT SUCCESS");
            return ResponseEntity.ok("Sign out success");
        } catch (Exception e) {
            log.error("SIGN OUT ERROR : {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Sign out failed");
        }
    }
}
