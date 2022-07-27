package com.example.week3day13project.mapper;

import com.example.week3day13project.domain.jdbc.Quiz;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuizRowMapper implements RowMapper<Quiz> {

    @Override
    public Quiz mapRow(ResultSet rs, int rowNum) throws SQLException {
        Quiz quiz = new Quiz();
        quiz.setQuizID(rs.getInt("quiz_id"));
        quiz.setQuizType(rs.getInt("quiz_type"));
        quiz.setScore(rs.getInt("score"));
        return quiz;
    }
}
