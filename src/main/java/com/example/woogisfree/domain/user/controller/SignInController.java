package com.example.woogisfree.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {

    @GetMapping("/signin")
    public String signIn() {
        return "signin";
    }

    @GetMapping("/signin-error")
    public String signInError(Model model) {
        model.addAttribute("loginError", true);
        return "signin";
    }
}
