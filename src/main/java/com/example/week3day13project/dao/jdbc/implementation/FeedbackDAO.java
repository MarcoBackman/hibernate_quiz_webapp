package com.example.week3day13project.dao.jdbc.implementation;

import com.example.week3day13project.dao.jdbc.FeedbackDAOScheme;
import com.example.week3day13project.domain.jdbc.Feedback;
import com.example.week3day13project.domain.jdbc.User;
import com.example.week3day13project.mapper.FeedbackRowMapper;
import com.example.week3day13project.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeedbackDAO implements FeedbackDAOScheme {

    JdbcTemplate jdbcTemplate;
    UserRowMapper userRowMapper;
    FeedbackRowMapper feedbackRowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public FeedbackDAO(JdbcTemplate jdbcTemplate,
                        NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                        UserRowMapper userRowMapper,
                        FeedbackRowMapper feedbackRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.feedbackRowMapper = feedbackRowMapper;
    }

    @Override
    public List<Feedback> findAll() {
        String query = "SELECT * FROM Feedback";
        return jdbcTemplate.query(query, feedbackRowMapper);
    }

    @Override
    public boolean checkFeedbackAvailability(String user_id) {
        String query = "SELECT * FROM User WHERE user_id = ?";
        List<User> listOfUsers = jdbcTemplate.query(query, userRowMapper, user_id);
        User targetUser = listOfUsers.get(0);
        System.out.println(targetUser.toString());
        if (targetUser.isHasRated()){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean uploadFeedback(String rate, String content) {
        String query = "INSERT INTO Feedback (rate, content)" +
                " values (:rate, :content)";
        Map<String, Object> params = new HashMap<>();
        params.put("rate",rate);
        params.put("content",content);
        int effectedRows = 0;
        try {
            effectedRows = namedParameterJdbcTemplate.update(query, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return effectedRows != 0;
    }
}
