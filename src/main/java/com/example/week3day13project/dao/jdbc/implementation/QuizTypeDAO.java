package com.example.week3day13project.dao.jdbc.implementation;

import com.example.week3day13project.domain.jdbc.QuizType;
import com.example.week3day13project.mapper.QuizTypeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuizTypeDAO {
    JdbcTemplate jdbcTemplate;
    QuizTypeRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QuizTypeDAO(JdbcTemplate jdbcTemplate,
                       NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                       QuizTypeRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public List<QuizType> getAllQuizTypes() {
        String query = "SELECT * FROM QuizType";
        return jdbcTemplate.query(query, rowMapper);
    }

    public QuizType findTypeByTypeNumber(String typeNumber) {
        String query = "SELECT * FROM QuizType WHERE quiz_type = ?";
        Optional<QuizType> result = jdbcTemplate.query(query, rowMapper, typeNumber).stream().findAny();
        return result.get();
    }
}
