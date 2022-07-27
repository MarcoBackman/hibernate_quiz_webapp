package com.example.week3day13project.service;

import com.example.week3day13project.dao.hibernate.implementation.UserDAOImpl;
import com.example.week3day13project.domain.hibernate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoService {
    private final UserDAOImpl hibernateUserDAO;

    @Autowired
    public UserInfoService(UserDAOImpl hibernateUserDAO) {
        this.hibernateUserDAO = hibernateUserDAO;
    }

    @Transactional
    public List<User> getAllUsers() {
        return hibernateUserDAO.findAll();
    }

    @Transactional
    public User getUserById(Integer userId) {
        return hibernateUserDAO.findById(userId);
    }

    @Transactional
    public void suspendUser(Integer userId) {
        hibernateUserDAO.suspendUser(userId);
    }

    @Transactional
    public void activateUser(Integer userId) {
        hibernateUserDAO.unSuspendUser(userId);
    }
}
