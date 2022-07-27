package com.example.week3day13project.dao.jdbc;

import com.example.week3day13project.domain.jdbc.Question;
import com.example.week3day13project.domain.jdbc.QuestionOption;

import java.util.List;
import java.util.Map;

public interface QuestionDAOScheme {
    List<Question> findAll();
    List<Question> findById(String questionID);
    //returns question id
    int addMultipleQuestion(String questionContent, String typeIndex); //Must map options after adding question
    //returns question id
    int addShortQuestion(String questionContent, String questionAnswer, String typeIndex);
    boolean updateMultipleQuestionContent(String questionID, String questionContent);
    boolean updateShortQuestionAnswer(String questionID, String questionAnswer);
    boolean updateShortQuestionContent(String questionID, String questionContent);
    boolean deleteQuestion(String questionID);
    boolean isShortQuestion(String questionID);
    Map<Question, List<QuestionOption>> getTenMultipleQuestionsByQuizType(String typeIndex);
    List<Question> getTwoShortQuestionsByQuizType(String typeIndex);
}
