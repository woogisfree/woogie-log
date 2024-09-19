package com.example.woogisfree.global.util;

import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserService userService;

    //TODO 브라우저 캐싱 문제로 인한 화면에 바로 적용 안됨 문제 해결 필요, profile image path 를 날짜에 따라 분리할 필요가 있음
    public void addCurrentUserToModel(Model model, Principal principal) {
        if (principal != null) {
            ApplicationUser currentUser = userService.findUserByUsername(principal.getName()).get();
            if (currentUser.getProfileImage() != null) {
                String profileImagePath = "/profile-images/" + currentUser.getProfileImage();
                currentUser.setProfileImage(profileImagePath);
            }
            model.addAttribute("currentUser", currentUser);
        }
    }
}
