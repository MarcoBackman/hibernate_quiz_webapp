package com.example.week3day13project.controller;

import com.example.week3day13project.security.PasswordHandler;
import com.example.week3day13project.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    private static final int SALT_LENGTH = 20;

    private static RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(value = "/register")
    public ModelAndView getRegister(Model model) {
        return new ModelAndView("register");
    }

    @PostMapping ("/register")
    public String registerUser(Model model,
                               @RequestParam("user_id") String userName,
                               @RequestParam("password") String userPW,
                               @RequestParam("first_name") String userFirstName,
                               @RequestParam("last_name") String userLastName,
                               @RequestParam("email") String userEmail) {
        //Check if user exists first
        int code = registerService.validateRegister(userName, userEmail);
        if (code == 1) { //email taken
            model.addAttribute("message", "You're already registered");
            return "register";
        } else if (code == 2) {
            model.addAttribute("message", "User name already taken use different name");
            return "register";
        } else { //register

            String generatedSalt = PasswordHandler.getSalt(SALT_LENGTH);
            //Hash password with salt value
            String hashedPassword = PasswordHandler.hashPassword(userPW.trim(), generatedSalt);

            //Save user data along with salted and hashed values into DB
            registerService.registerUser(userName, hashedPassword, generatedSalt, userEmail, userFirstName, userLastName);
            return "loginPage";
        }
    }

}
