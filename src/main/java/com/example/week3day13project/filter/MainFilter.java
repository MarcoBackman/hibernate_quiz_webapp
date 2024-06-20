package com.example.week3day13project.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@WebFilter(urlPatterns = "/*")
public class MainFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException {

        HttpSession session = request.getSession(false);

        //when user session is valid
        if (session != null && session.getAttribute("userObject") != null) {
            //add when valid user enters wrong address
            response.sendRedirect("/home");
        } else {
            System.out.println("Invalid user session");
            // redirect back to the login page if user is not logged in
            response.sendRedirect("/login");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        //Consider changing this to switch case; if-else will decrease speed
        switch (path) {
            case "/login":
            case "/contact-us":
            case "/register":
                return true;
            case "/home":
            case "/logout":
            case "/feedback":
            case "/question-editor":
            case "/user-profile":
            case "/suspend_user":
            case "/activate_user":
            case "/add_multiple_question":
            case "/add_short_question":
            case "/submit_multiple_change":
            case "/delete_question":
            case "/take-quiz":
            case "/previous":
            case "/next":
            case "/disable_question":
            case "/activate_question":
                HttpSession session = request.getSession(false);
                return session != null && session.getAttribute("userObject") != null;
        }

        //Take quiz topic choose matcher
        final Pattern pattern
                = Pattern.compile("^(take-quiz)$|(take-quiz\\?+topic-info=[0-9]+\\/[0-9]+)");
        final Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            HttpSession session = request.getSession(false);
            return session != null && session.getAttribute("userObject") != null;
        }

        //Take quiz question numbering matcher
        final Pattern pattern2
                = Pattern.compile("^(take-quiz)$|(take-quiz\\/[0-9]+\\/[0-9]+)|(take-quiz\\/[0-9]+)");
        final Matcher matcher2 = pattern2.matcher(path);
        if (matcher2.find()) {
            HttpSession session = request.getSession(false);
            return session != null && session.getAttribute("userObject") != null;
        }

        //Submit quiz matcher
        final Pattern pattern3
                = Pattern.compile("^\\/.*(submit_quiz)$|\\/.*(submit_quiz\\/[0-9]+)");
        final Matcher matcher3 = pattern3.matcher(path);
        if (matcher3.find()) {
            HttpSession session = request.getSession(false);
            return session != null && session.getAttribute("userObject") != null;
        }

        //Admin page matcher
        final Pattern pattern4
                = Pattern.compile("^\\/(admin-page)\\/(log-detail){1}\\/([0-9]+)" +
                "|^\\/(admin-page)\\/{0,1}$" +
                "|\\/(admin-page)\\/(by-category){1}" +
                "|\\/(admin-page)\\/(by-user){1}");
        final Matcher matcher4 = pattern4.matcher(path);
        if (matcher4.find()) {
            HttpSession session = request.getSession(false);
            return session != null && session.getAttribute("userObject") != null;
        }

        System.out.println("redirecting to login or home....");
        return false;
    }
}
