package com.example.week3day13project.mapper;

import com.example.week3day13project.domain.jdbc.UserQuestion;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserQuestionRowMapper implements RowMapper<UserQuestion> {
    @Override
    public UserQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setUserQuestionID(rs.getInt("user_question_id"));
        userQuestion.setQuizID(rs.getInt("quiz_id"));
        userQuestion.setQuestionID(rs.getInt("question_id"));
        userQuestion.setUserAnswer(rs.getString("user_answer"));
        userQuestion.setSelected_option_id(rs.getInt("selected_option_id"));
        userQuestion.setShort_question(rs.getBoolean("is_short_question"));
        return userQuestion;
    }
}
