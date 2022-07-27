package com.example.week3day13project.controller;

import com.example.week3day13project.domain.hibernate.User;
import com.example.week3day13project.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ViewUserController {

    private final UserInfoService userInfoService;
    private User targetUser;

    public ViewUserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping(value = "/user-profile")
    public String viewUserInfo(HttpServletRequest request, Model model) {
        String userId = request.getParameter("user_info");
        User user = userInfoService.getUserById(Integer.parseInt(userId));
        model.addAttribute("user", user);
        this.targetUser = user;
        return "userProfile";
    }

    @PostMapping("/activate_user")
    public String activateUser() {
        if (targetUser != null) {
            userInfoService.activateUser(targetUser.getUserId());
            targetUser.setSuspended(false);
        }
        return "redirect:/admin-page";
    }

    @PostMapping("/suspend_user")
    public String suspendUser() {
        if (targetUser != null) {
            userInfoService.suspendUser(targetUser.getUserId());
            targetUser.setSuspended(true);
        }
        return "redirect:/admin-page";
    }
}
