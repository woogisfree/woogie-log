package com.example.woogisfree.domain.mypage.web;

import com.example.woogisfree.domain.mypage.service.MyPageService;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/my-page")
@Controller
public class MyPageViewController {

    private final MyPageService myPageService;
    private final UserService userService;

    @GetMapping
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Long userId = userService.getUserIdFromUserDetails(userDetails);
        model.addAttribute("user", userService.findUserById(userId));
        log.info("user={}", userService.findUserById(userId));
        return "my-page";
    }


}
