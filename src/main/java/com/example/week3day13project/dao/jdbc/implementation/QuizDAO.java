package com.example.week3day13project.dao.jdbc.implementation;

import com.example.week3day13project.domain.jdbc.Quiz;
import com.example.week3day13project.mapper.QuizRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component
public class QuizDAO {
    JdbcTemplate jdbcTemplate;
    QuizRowMapper quizRowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QuizDAO(QuizRowMapper quizRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.quizRowMapper = quizRowMapper;
    }

    public List<Quiz> findByQuizTypeIndex(String quizTypeIndex) {
        String query = "SELECT * FROM Quiz Where quiz_type = ?";
        return jdbcTemplate.query(query, quizRowMapper, quizTypeIndex);
    }

    public int createQuiz(String quizTypeIndex, String score) {
        String quizQuery = "INSERT INTO Quiz (quiz_type, score) values (?, ?)";

        //For auto generated primary key retrieval
        KeyHolder keyHolder  = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(quizQuery, new String[]{"quiz_id"});
            ps.setString(1, quizTypeIndex);
            ps.setString(2, score);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public List<Quiz> findByID(String quizID) {
        String query = "SELECT * FROM Quiz WHERE quiz_id = ?";
        return jdbcTemplate.query(query, quizRowMapper, quizID);
    }

}
