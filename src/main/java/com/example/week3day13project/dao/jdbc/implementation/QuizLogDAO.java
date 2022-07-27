package com.example.week3day13project.dao.jdbc.implementation;

import com.example.week3day13project.domain.jdbc.QuizLog;
import com.example.week3day13project.mapper.QuizLogRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class QuizLogDAO {
    JdbcTemplate jdbcTemplate;
    QuizLogRowMapper quizLogRowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QuizLogDAO(JdbcTemplate jdbcTemplate,
                      NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                      QuizLogRowMapper QuizLogRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.quizLogRowMapper = QuizLogRowMapper;
    }

    public void insertQuizLog(String userID, String timeStart, String timeEnd, String quizID) {
        String query = "INSERT INTO QuizLog (user_id, time_start, time_end, quiz_id) " +
                "values (?, ?, ?, ?)";

        //For auto generated primary key retrieval
        KeyHolder keyHolder  = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, new String[]{"log_id"});
            ps.setString(1, userID);
            ps.setString(2, timeStart);
            ps.setString(3, timeEnd);
            ps.setString(4, quizID);
            return ps;
        }, keyHolder);
    }

    //Ordered by date in default
    public List<QuizLog> findAll() {
        String query = "SELECT * FROM QuizLog ORDER BY time_start DESC";
        return jdbcTemplate.query(query, quizLogRowMapper);
    }

    //Ordered by date in default
    public QuizLog findByQuizID(String quizID) {
        String query = "SELECT * FROM QuizLog WHERE quiz_id = ?";
        return jdbcTemplate.query(query, quizLogRowMapper, quizID).stream().findFirst().orElse(new QuizLog());
    }

    //Ordered by date in default
    public List<QuizLog> findByUserID(String userID) {
        String query = "SELECT * FROM QuizLog WHERE user_id = ? ORDER BY time_start DESC";
        return jdbcTemplate.query(query, quizLogRowMapper, userID);
    }
}
