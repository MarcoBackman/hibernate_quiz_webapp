package com.example.week3day13project.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class DBProperty {

    @Value("${database.url}")
    private String url;

    @Value("${database.driver}")
    private String driver;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Value("${database.hibernate.dialect}")
    private String dialect;

    @Value("${database.hibernate.showsql}")
    private String showsql;

    @Value("${database.hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

}
