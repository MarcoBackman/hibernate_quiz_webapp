package com.example.week3day13project.dao.jdbc.implementation;

import com.example.week3day13project.dao.jdbc.UserDAOScheme;
import com.example.week3day13project.domain.jdbc.User;
import com.example.week3day13project.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDAO implements UserDAOScheme {

    JdbcTemplate jdbcTemplate;
    UserRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate,
                     NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                     UserRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM User";
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<User> findById(String userID) {
        String query = "SELECT * FROM User WHERE user_id = ?";
        return jdbcTemplate.query(query, rowMapper, userID);
    }

    @Override
    public List<User> findByName(String account_name) {
        String query = "SELECT * FROM User WHERE user_name = ?";
        return jdbcTemplate.query(query, rowMapper, account_name);
    }

    @Override
    public boolean insertUser(String userName,
                              String userPW,
                              String email,
                              String firstName,
                              String lastName) {
        String query = "INSERT INTO User (user_name, user_pw, email, first_name, last_name)" +
                " values (:user_name, :user_pw, :email, :first_name, :last_name)";
        Map<String, Object> params = new HashMap<>();
        params.put("user_name",userName);
        params.put("user_pw",userPW);
        params.put("email",email);
        params.put("first_name",firstName);
        params.put("last_name",lastName);
        int effectedRows = 0;
        try {
            effectedRows = namedParameterJdbcTemplate.update(query, params);
        } catch (DuplicateKeyException e) {
            System.out.println("Key already exists");
        }
        return effectedRows != 0;
    }


    @Override
    public boolean deleteUser(String userID) {
        String query = "DELETE FROM User WHERE user_id = ?";

        int effectedRows = 0;
        try {
            effectedRows = jdbcTemplate.update(query, userID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return effectedRows == 1;
    }

    @Override
    //will return true when effected
    public boolean suspendUser(String userID) {
        String query = "UPDATE User SET is_suspended = 1 WHERE user_id = ?";
        int effectedRows = jdbcTemplate.update(query, userID);
        return effectedRows != 0;
    }

    @Override
    //will return true when effected
    public boolean unSuspendUser(String userID) {
        String query = "UPDATE User SET is_suspended = 0 WHERE user_id = ?";
        int effectedRows = jdbcTemplate.update(query, userID);
        return effectedRows != 0;
    }

    @Override
    public boolean grantAdmin(String userKey) {
        return false;
    }

    @Override
    public boolean setHasRated(String userID) {
        String query = "UPDATE User SET has_rated = 1 WHERE user_id = ?";
        int effectedRows = jdbcTemplate.update(query, userID);
        return effectedRows != 0;
    }

    public Optional<User> validateLogin(String username, String password) {
        String query = "SELECT * FROM User WHERE user_name = ? AND user_pw = ?";
        List<User> resultByEmail = jdbcTemplate.query(query, rowMapper, username, password);
        return resultByEmail.stream().findFirst();
    }

    public Optional<User> getByEmail(String userEmail) {
        String query_email = "SELECT * FROM User WHERE email = ?";
        List<User> resultByEmail = jdbcTemplate.query(query_email, rowMapper, userEmail);
        return resultByEmail.stream().findAny();
    }

    public Optional<User> getByUserName(String username) {
        String query_name = "SELECT * FROM User WHERE user_name = ?";
        List<User> resultByName = jdbcTemplate.query(query_name, rowMapper, username);
        return resultByName.stream().findAny();
    }
}
