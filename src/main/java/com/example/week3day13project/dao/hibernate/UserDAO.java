package com.example.week3day13project.dao.hibernate;

import com.example.week3day13project.domain.hibernate.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll();
    User findById(Integer userID);
    User findByName(String accountName);
    void insertUser(User newUser);
    void setUserRated(Integer userID);
    User validateLogin(String userName, String password);
    User getByEmail(String userEmail);
    User getByUserName(String userName);
    void suspendUser(Integer userID);
    void unSuspendUser(Integer userID);
}
