package com.example.week3day13project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Week3Day13ProjectApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Week3Day13ProjectApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Week3Day13ProjectApplication.class, args);
    }
}
