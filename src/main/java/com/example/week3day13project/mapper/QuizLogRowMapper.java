package com.example.week3day13project.mapper;

import com.example.week3day13project.domain.jdbc.QuizLog;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuizLogRowMapper implements RowMapper<QuizLog> {

    @Override
    public QuizLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        QuizLog quizLog = new QuizLog();
        quizLog.setLogID(rs.getInt("log_id"));
        quizLog.setUserID(rs.getInt("user_id"));
        quizLog.setTimeStart(rs.getString("time_start"));
        quizLog.setTimeEnd(rs.getString("time_end"));
        quizLog.setQuizID(rs.getInt("quiz_id"));
        return quizLog;
    }
}
