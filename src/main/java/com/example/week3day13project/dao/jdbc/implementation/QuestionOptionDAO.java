package com.example.week3day13project.dao.jdbc.implementation;

import com.example.week3day13project.dao.jdbc.QuestionOptionDAOScheme;
import com.example.week3day13project.domain.jdbc.Question;
import com.example.week3day13project.domain.jdbc.QuestionOption;
import com.example.week3day13project.mapper.QuestionOptionRowMapper;
import com.example.week3day13project.mapper.QuestionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionOptionDAO implements QuestionOptionDAOScheme {

    JdbcTemplate jdbcTemplate;
    QuestionOptionRowMapper rowMapper;
    QuestionRowMapper questionRowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QuestionOptionDAO(JdbcTemplate jdbcTemplate,
                             NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                             QuestionOptionRowMapper rowMapper,
                             QuestionRowMapper questionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
        this.questionRowMapper = questionRowMapper;
    }

    /**
     * Use this to get 5 options
     */
    @Override
    public List<QuestionOption> findAll() {
        String query = "SELECT * FROM QuestionOption";
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<QuestionOption> findById(String optionID) {
        String query = "SELECT * FROM QuestionOption WHERE option_id = ?";
        return jdbcTemplate.query(query, rowMapper, optionID);
    }

    public Map<Integer, List<QuestionOption>> findAllAndGroupByQuestionID() {
        String query = "SELECT * FROM Question";
        List<Question> questions = jdbcTemplate.query(query, questionRowMapper);

        Map<Integer, List<QuestionOption>> group = new HashMap<>();

        for (Question question : questions) {
            String inner_query = "SELECT * FROM QuestionOption WHERE question_id = ?";
            List<QuestionOption> tempStorage = jdbcTemplate.query(inner_query, rowMapper, question.getQuestionID());
            group.put(question.getQuestionID(), tempStorage);
        }

        return group;
    }

    @Override
    public void addOption(String optionContent, String isAnswer, String questionID) {
        String query = "INSERT INTO QuestionOption (option_content, is_answer, question_id)" +
                " values (:option_content, :is_answer, :question_id)";
        Map<String, Object> params = new HashMap<>();
        params.put("option_content",optionContent);
        params.put("is_answer",isAnswer);
        params.put("question_id",questionID);
        namedParameterJdbcTemplate.update(query, params);
    }

    //Only use this on question delete
    @Override
    public void deleteAllOption(String questionID) {
        //Set safe option false to delete options by value
        String option_query = "SET SQL_SAFE_UPDATES = 0";
        jdbcTemplate.update(option_query);
        String query = "DELETE FROM QuestionOption WHERE question_id = ?";
        jdbcTemplate.update(query, questionID);
    }

    @Override
    public void editOption(String optionContent, String isAnswer, String questionID) {
        String query = "UPDATE QuestionOption SET option_content = ?, is_answer = ?  WHERE question_id = ?";
        jdbcTemplate.update(query, optionContent, isAnswer, questionID);
    }

    public List<QuestionOption> getOptionsByQuestionID(String questionID) {
        String query = "SELECT * FROM QuestionOption WHERE question_id = ?";
        return jdbcTemplate.query(query, rowMapper, questionID);
    }
}
