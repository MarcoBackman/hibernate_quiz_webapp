package com.example.week3day13project.mapper;

import com.example.week3day13project.domain.jdbc.Question;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuestionRowMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setQuestionID(rs.getInt("question_id"));
        question.setQuestionContent(rs.getString("question_content"));
        question.setShortQuestionAnswer(rs.getString("short_question_answer"));
        question.setActive(rs.getBoolean("is_active"));
        question.setShortQuestion(rs.getBoolean("is_short_question"));
        question.setQuizType(rs.getInt("quiz_type"));
        return question;
    }
}
