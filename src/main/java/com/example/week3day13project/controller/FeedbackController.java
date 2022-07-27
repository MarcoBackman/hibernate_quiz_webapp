package com.example.week3day13project.controller;

import com.example.week3day13project.domain.hibernate.User;
import com.example.week3day13project.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController (FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @RequestMapping(value = "/feedback")
    public String getFeedbackPage(HttpServletRequest request) {
        HttpSession currentSession = request.getSession(false);

        if (currentSession == null) { //Expired or no session
            return "loginPage";
        } else {
            User user = (User)currentSession.getAttribute("userObject");
            //String userID = String.valueOf(user.getUserId());
            //check if user has already given a feedback
            System.out.println("Feedback requested for id: " + user.getUserId());
            if (feedbackService.allowedToFeedback(user.getUserId())) {
                return "feedbackPage";
            } else { //if not, redirect to the page
                System.out.println("User has already rated");
                return "mainPage";
            }
        }
    }

    @PostMapping("/feedback")
    public String insertFeedback(@RequestParam("rating") String rate,
                                 @RequestParam("feedback") String content,
                                 HttpServletRequest request) {
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) { //Expired or no session
            return "loginPage";
        } else {
            User user = (User) currentSession.getAttribute("userObject");
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            feedbackService.leaveFeedBack(Integer.parseInt(rate), dtf.format(date), content);
            feedbackService.updateUserFeedbackStatus(user.getUserId());
        }
        return "redirect:mainPage";
    }
}
