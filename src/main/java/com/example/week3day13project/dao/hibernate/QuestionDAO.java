package com.example.week3day13project.dao.hibernate;

import com.example.week3day13project.domain.hibernate.Question;
import com.example.week3day13project.domain.hibernate.QuestionOption;

import java.util.LinkedHashMap;
import java.util.List;

public interface QuestionDAO {
    List<Question> findAll();
    Question findByQuestionID(Integer questionID);
    List<Question> findAllMultipleQuestionByType(Integer typeIndex);
    List<Question> findAllShortQuestionByType(Integer typeIndex);
    Integer addQuestion(Question question);
    void updateQuestionContent(Integer questionID, String content);
    void deleteQuestion(Integer questionID);
    void disableQuestion(Integer questionID);
    void activateQuestion(Integer questionID);
    boolean isShortQuestion(Integer questionID);
    LinkedHashMap<Question, List<QuestionOption>> getTenMultipleQuestionsByQuizType(Integer typeIndex);
    List<Question> getTwoShortQuestionsByQuizType(Integer typeIndex);
}
