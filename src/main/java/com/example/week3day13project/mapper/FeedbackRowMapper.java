package com.example.week3day13project.mapper;

import com.example.week3day13project.domain.jdbc.Feedback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FeedbackRowMapper implements RowMapper<Feedback> {

    @Override
    public Feedback mapRow(ResultSet rs, int rowNum) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setRate(rs.getFloat("rate"));
        feedback.setDate(rs.getDate("date"));
        feedback.setContent(rs.getString("content"));
        return feedback;
    }
}