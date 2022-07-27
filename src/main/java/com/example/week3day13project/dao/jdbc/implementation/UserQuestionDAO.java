package com.example.week3day13project.dao.jdbc.implementation;

import com.example.week3day13project.domain.jdbc.UserQuestion;
import com.example.week3day13project.mapper.UserQuestionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class UserQuestionDAO {

    JdbcTemplate jdbcTemplate;
    UserQuestionRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserQuestionDAO(JdbcTemplate jdbcTemplate,
                           NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                           UserQuestionRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public List<UserQuestion> findAll() {
        String query = "SELECT * FROM UserQuestion";
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<UserQuestion> findAllByQuizID(String quizID) {
        String query = "SELECT * FROM UserQuestion WHERE quiz_id = ?";
        return jdbcTemplate.query(query, rowMapper, quizID);
    }

    public void insertUserQuestion(String quizID,
                                   String questionID,
                                   String userAnswer,
                                   String selectedOptionID,
                                   String isShortQuestion) {
        String query = "INSERT INTO UserQuestion (quiz_id, question_id, user_answer, selected_option_id, is_short_question) " +
                "values (?, ?, ?, ?, ?)";

        //For auto generated primary key retrieval
        KeyHolder keyHolder  = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, new String[]{"log_id"});
            ps.setString(1, quizID);
            ps.setString(2, questionID);
            ps.setString(3, userAnswer);
            ps.setString(4, selectedOptionID);
            ps.setString(5, isShortQuestion);
            return ps;
        }, keyHolder);
    }

}
