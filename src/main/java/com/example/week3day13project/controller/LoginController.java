package com.example.week3day13project.controller;

import com.example.week3day13project.domain.hibernate.User;
import com.example.week3day13project.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login")
    public String showLoginPage(Model model) {
        return "loginPage";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam("user_id") String username,
                            @RequestParam("password") String password,
                            HttpServletRequest request) {

        User possibleUser = loginService.validateLogin(username, password);


        if(possibleUser != null) {

            //Check if the user has been suspended
            if(possibleUser.isSuspended()) {
                return "loginPage";
            }

            HttpSession oldSession = request.getSession(false);
            // invalidate old session if it exists
            if (oldSession != null) oldSession.invalidate();

            // generate new session
            HttpSession newSession = request.getSession(true);

            // store user object in session
            newSession.setAttribute("userObject", possibleUser);

            return "redirect:home";
        } else {
            return "loginPage";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession oldSession = request.getSession(false);
        User user = (User)oldSession.getAttribute("userObject");

        // invalidate old session if it exists
        oldSession.invalidate();
        return "loginPage";
    }
}
