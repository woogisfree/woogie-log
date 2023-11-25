package com.example.woogisfree.domain.user.controller;

import com.example.woogisfree.domain.user.dto.AddUserRequest;
import com.example.woogisfree.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new AddUserRequest());
        return "register";
    }

    //TODO Email unique, password != confirm password => Error message if it is different when going to the next tab
    // try catch (EmailAlreadyExistsException | PasswordMismatchException e) {
    //       e instanceof EmailAlreadyExistsException
    //       model.addAttribute("errorMessage", "Email already exists. Please choose a different email.");
    //       return "register";
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") AddUserRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "register";
        }

        userService.createUser(request);
        return "redirect:register?success";
    }
}

