package com.example.week3day13project.dao.jdbc;

import com.example.week3day13project.domain.jdbc.User;

import java.util.List;

public interface UserDAOScheme {
    List<User> findAll();
    List<User> findById(String userKey);
    List<User> findByName(String name);
    boolean insertUser(String userName,
                       String userPW,
                       String email,
                       String firstName,
                       String lastName);

    boolean deleteUser(String userKey);
    boolean suspendUser(String userKey);
    boolean unSuspendUser(String userKey);
    boolean grantAdmin(String userKey); //This is only for a test version
    boolean setHasRated(String userID);
}
