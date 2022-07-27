package com.example.week3day13project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class Week3Day13ProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(Week3Day13ProjectApplication.class, args);
    }
}
