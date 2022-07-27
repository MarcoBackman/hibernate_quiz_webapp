package com.example.week3day13project.mapper;

import com.example.week3day13project.domain.jdbc.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUserName(rs.getString("user_name"));
        user.setSuspended(rs.getBoolean("is_suspended"));
        user.setAdmin(rs.getBoolean("is_admin"));
        user.setHasRated(rs.getBoolean("has_rated"));
        user.setLastName(rs.getString("last_name"));
        user.setFirstName(rs.getString("first_name"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
