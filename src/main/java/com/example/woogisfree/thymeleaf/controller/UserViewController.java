package com.example.woogisfree.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "signIn.html";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signUp.html";
    }
}
