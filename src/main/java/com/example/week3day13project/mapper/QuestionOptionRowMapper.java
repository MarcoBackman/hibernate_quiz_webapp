package com.example.week3day13project.mapper;

import com.example.week3day13project.domain.jdbc.QuestionOption;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuestionOptionRowMapper implements RowMapper<QuestionOption> {

    @Override
    public QuestionOption mapRow(ResultSet rs, int rowNum) throws SQLException {
        return QuestionOption.builder()
                .optionID(rs.getInt("option_id"))
                .optionContent(rs.getString("option_content"))
                .answer(rs.getBoolean("is_answer"))
                .questionID(rs.getInt("question_id")).build();
    }
}