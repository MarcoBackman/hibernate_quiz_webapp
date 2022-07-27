package com.example.week3day13project.mapper;

import com.example.week3day13project.domain.jdbc.QuizType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuizTypeRowMapper implements RowMapper<QuizType> {

    @Override
    public QuizType mapRow(ResultSet rs, int rowNum) throws SQLException {
        QuizType quizType = new QuizType();
        quizType.setQuizTypeNumber(rs.getInt("quiz_type"));
        quizType.setQuizDescription(rs.getString("type_detail"));
        return quizType;
    }
}
