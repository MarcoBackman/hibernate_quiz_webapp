package com.example.week3day13project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ContactUsController {
    @RequestMapping(value = "/contact-us")
    public String getContactUsPage() {
        return "contactUsPage";
    }
}
