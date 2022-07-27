package com.example.week3day13project.service;

import com.example.week3day13project.dao.hibernate.implementation.QuestionDAOImpl;
import com.example.week3day13project.dao.hibernate.implementation.QuestionOptionDAOImpl;
import com.example.week3day13project.domain.hibernate.Question;
import com.example.week3day13project.domain.hibernate.QuestionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {
    private final QuestionDAOImpl hibernateQuestionDAO;
    private final QuestionOptionDAOImpl hibernateQuestionOptionDAO;

    @Autowired
    public QuestionService(QuestionDAOImpl hibernateQuestionDAO,
                           QuestionOptionDAOImpl hibernateQuestionOptionDAO) {
        this.hibernateQuestionDAO = hibernateQuestionDAO;
        this.hibernateQuestionOptionDAO = hibernateQuestionOptionDAO;
    }

    @Transactional
    public List<Question> getAllQuestionsByType(Integer questionType) {
        return hibernateQuestionDAO.findAllByType(questionType);
    }

    @Transactional
    public List<Question> getAllMultipleQuestionsByType(Integer questionType) {
        return hibernateQuestionDAO.findAllMultipleQuestionByType(questionType);
    }

    @Transactional
    public List<Question> getAllShortQuestionsByType(Integer questionType) {
        return hibernateQuestionDAO.findAllShortQuestionByType(questionType);
    }

    @Transactional
    public void addMultipleQuestionWithOptions(String questionContent,
                                               Integer quizType,
                                               List<QuestionOption> options) {
        //Create option-empty question first
        Question question = Question.builder()
                .questionContent(questionContent)
                .shortQuestionAnswer(null)
                .active(true)
                .quizType(quizType)
                .shortQuestion(false).build();

        int questionIndex = hibernateQuestionDAO.addQuestion(question);

        //Map options to question index on creation
        for (QuestionOption option : options) {
            QuestionOption tempOption = QuestionOption.builder()
                    .optionContent(option.getOptionContent())
                    .answer((option.isAnswer() ? true : false))
                    .questionID(questionIndex).build();
            hibernateQuestionOptionDAO.addOption(tempOption);
        }
    }

    @Transactional
    public int addShortQuestion(String questionContent,
                                String answer,
                                Integer quizType) {
        //Create option-empty question first
        Question question = Question.builder()
                .questionContent(questionContent)
                .shortQuestionAnswer(answer)
                .active(true)
                .quizType(quizType)
                .shortQuestion(true).build();

        return hibernateQuestionDAO.addQuestion(question);
    }

    //Currently, Cannot change options.
    @Transactional
    public void changeMultipleQuestion(Integer questionID, String questionContent) {
        hibernateQuestionDAO.updateQuestionContent(questionID, questionContent);
    }

    @Transactional
    public void deleteMultipleQuestion(Integer questionID) {
        //delete question
        hibernateQuestionDAO.deleteQuestion(questionID);
        hibernateQuestionOptionDAO.deleteAllOptions(questionID);
    }

    @Transactional
    public void disableQuestion(Integer questionID) {
        //delete question
        hibernateQuestionDAO.disableQuestion(questionID);
    }

    @Transactional
    public void activateQuestion(Integer questionID) {
        //delete question
        hibernateQuestionDAO.activateQuestion(questionID);
    }

    @Transactional
    public Map<Integer, List<QuestionOption>> getAllOptionsByGroup() {
        //delete question
        return hibernateQuestionOptionDAO.findAllAndGroupByQuestionID();
    }

    @Transactional
    public LinkedHashMap<Question, List<QuestionOption>> getTenMultipleQuestionsByType(Integer questionType) {
        return hibernateQuestionDAO.getTenMultipleQuestionsByQuizType(questionType);
    }

    @Transactional
    public List<Question> getTwoShortQuestionsByType(Integer questionType) {
        return hibernateQuestionDAO.getTwoShortQuestionsByQuizType(questionType);
    }

    @Transactional
    public Question getQuestionByQuestionID(Integer questionID) {
        return hibernateQuestionDAO.findById(questionID);
    }

    @Transactional
    public List<QuestionOption> getQuestionOptionsByQuestionID(Integer questionID) {
        return hibernateQuestionOptionDAO.getOptionsByQuestionID(questionID);
    }

}
