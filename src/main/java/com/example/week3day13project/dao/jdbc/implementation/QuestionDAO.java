package com.example.week3day13project.dao.jdbc.implementation;

import com.example.week3day13project.dao.jdbc.QuestionDAOScheme;
import com.example.week3day13project.domain.jdbc.Question;
import com.example.week3day13project.domain.jdbc.QuestionOption;
import com.example.week3day13project.mapper.QuestionOptionRowMapper;
import com.example.week3day13project.mapper.QuestionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.*;

@Component
public class QuestionDAO implements QuestionDAOScheme {

    JdbcTemplate jdbcTemplate;
    QuestionRowMapper rowMapper;
    QuestionOptionRowMapper questionOptionRowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QuestionDAO(JdbcTemplate jdbcTemplate,
                        NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                        QuestionRowMapper rowMapper,
                        QuestionOptionRowMapper questionOptionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
        this.questionOptionRowMapper = questionOptionRowMapper;
    }

    @Override
    public List<Question> findAll() {
        String query = "SELECT * FROM Question";
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<Question> findById(String questionID) {
        String query = "SELECT * FROM Question WHERE question_id = ?";
        return jdbcTemplate.query(query, rowMapper, questionID);
    }

    public List<Question> findAllMultipleQuestionByType(String typeIndex) {
        String query = "SELECT * FROM Question where quiz_type = ? and is_short_question = 0";
        return jdbcTemplate.query(query, rowMapper, typeIndex);
    }

    public List<Question> findAllShortQuestionByType(String typeIndex) {
        String query = "SELECT * FROM Question where quiz_type = ? and is_short_question = 1";
        return jdbcTemplate.query(query, rowMapper, typeIndex);
    }

    /**
     * Note: This does not add options that belong to this question
     * @param questionContent Question content
     * @param typeIndex Question type index (e.g. math, science ...)
     * @return question key
     */
    //Must map options after adding question
    @Override
    public int addMultipleQuestion(String questionContent, String typeIndex) {
        String questionQuery = "INSERT INTO Question (question_content, is_short_question, quiz_type) " +
                "values (?, ?, ?)";

        //For auto generated primary key retrieval
        KeyHolder keyHolder  = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(questionQuery, new String[]{"question_id"});
            ps.setString(1, questionContent);
            ps.setString(2, "0");
            ps.setString(3, typeIndex);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    /**
     *
     * @param questionContent Question content
     * @param questionAnswer Answer for the content
     * @param typeIndex Question type index (e.g. math, science ...)
     * @return question key
     */
    @Override
    public int addShortQuestion(String questionContent, String questionAnswer, String typeIndex) {
        String questionQuery = "INSERT INTO Question (question_content, short_question_answer, is_short_question, quiz_type) " +
                "values (?, ?, ?, ?)";

        //For auto generated primary key retrieval
        KeyHolder keyHolder  = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(questionQuery, new String[]{"question_id"});
            ps.setString(1, questionContent);
            ps.setString(2, questionAnswer);
            ps.setString(3, "1");
            ps.setString(4, typeIndex);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    //careful not to change short question on multiple question or vice versa.
    @Override
    public boolean updateMultipleQuestionContent(String questionID, String questionContent) {
        String query = "UPDATE Question SET question_content = ? WHERE question_id = ?";
        int effectedRows = jdbcTemplate.update(query, questionContent, questionID);
        return effectedRows != 0;
    }

    @Override
    public boolean updateShortQuestionAnswer(String questionID, String questionAnswer) {
        String query = "UPDATE Question SET short_question_answer = ? WHERE question_id = ?";
        int effectedRows = jdbcTemplate.update(query, questionAnswer, questionID);
        return effectedRows != 0;
    }

    @Override
    public boolean updateShortQuestionContent(String questionID, String questionContent) {
        String query = "UPDATE Question SET question_content = ? WHERE question_id = ?";
        int effectedRows = jdbcTemplate.update(query, questionContent, questionID);
        return effectedRows != 0;
    }

    @Override
    public boolean deleteQuestion(String questionID) {
        String query = "DELETE FROM Question WHERE question_id = ?";
        int effectedRows = jdbcTemplate.update(query, questionID);
        return effectedRows != 0;
    }

    public boolean disableQuestion(String questionID) {
        String query = "UPDATE Question SET is_active = FALSE WHERE question_id = ?";
        int effectedRows = jdbcTemplate.update(query, questionID);
        return effectedRows != 0;
    }

    public boolean activateQuestion(String questionID) {
        String query = "UPDATE Question SET is_active = TRUE WHERE question_id = ?";
        int effectedRows = jdbcTemplate.update(query, questionID);
        return effectedRows != 0;
    }

    @Override
    public boolean isShortQuestion(String questionID) {
        String query = "SELECT * FROM Question WHERE question_id = ?";
        List<Question> questions = jdbcTemplate.query(query, rowMapper, questionID);
        return questions.stream().anyMatch(Question::isShortQuestion);
    }

    @Override
    public LinkedHashMap<Question, List<QuestionOption>> getTenMultipleQuestionsByQuizType(String typeIndex) {
        //Get ten questions
        String query = "SELECT * FROM Question WHERE quiz_type = ? AND is_short_question = FALSE" +
                " ORDER BY RAND() LIMIT 10";
        List<Question> questionList = jdbcTemplate.query(query, rowMapper, typeIndex);

        //Get ten questions
        LinkedHashMap<Question, List<QuestionOption>> questionSet = new LinkedHashMap<>();
        for (Question question : questionList) {
            String query_options = "SELECT * FROM QuestionOption WHERE question_id = ?";
            List<QuestionOption> optionList = jdbcTemplate.query(query_options, questionOptionRowMapper, question.getQuestionID());
            questionSet.put(question, optionList);
        }

        return questionSet;
    }

    @Override
    public List<Question> getTwoShortQuestionsByQuizType(String typeIndex) {
        String query = "SELECT * FROM Question WHERE quiz_type = ? AND is_short_question = TRUE" +
                " ORDER BY RAND() LIMIT 2";
        return jdbcTemplate.query(query, rowMapper, typeIndex);
    }
}
