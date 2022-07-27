package com.example.week3day13project.service;

import com.example.week3day13project.dao.hibernate.implementation.UserDAOImpl;
import com.example.week3day13project.domain.hibernate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private final UserDAOImpl hibernateUserDAO;

    @Autowired
    public RegisterService(UserDAOImpl hibernateUserDAO) {
        this.hibernateUserDAO = hibernateUserDAO;
    }

    @Transactional
    public int validateRegister(String userName, String userEmail) {
        User userByName = hibernateUserDAO.getByUserName(userName);
        User userByEmail = hibernateUserDAO.getByEmail(userEmail);
        if (userByEmail != null) {
            return 1;
        } else if (userByName != null) {
            return 2;
        } else {
            return 0;
        }
    }

    @Transactional
    public void registerUser(String userName,
                            String userPW,
                            String userEmail,
                            String userFirstName,
                            String userLastName) {
        User user = User.builder()
                .userName(userName)
                .userPW(userPW)
                .email(userEmail)
                .firstName(userFirstName)
                .lastName(userLastName).build();
        hibernateUserDAO.insertUser(user);
    }
}
