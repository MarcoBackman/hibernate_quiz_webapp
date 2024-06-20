package com.example.week3day13project.service;

import com.example.week3day13project.dao.hibernate.implementation.UserDAOImpl;
import com.example.week3day13project.domain.hibernate.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    private final UserDAOImpl hibernateUserDao;

    public LoginService(UserDAOImpl hibernateUserDao) {
        this.hibernateUserDao = hibernateUserDao;
    }

    @Transactional
    public User validateLogin(String username, String password) {
        return hibernateUserDao.validateLogin(username, password);
    }
}
