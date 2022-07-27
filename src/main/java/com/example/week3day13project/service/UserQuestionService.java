package com.example.week3day13project.service;

import com.example.week3day13project.dao.hibernate.implementation.UserQuestionDAOImpl;
import com.example.week3day13project.domain.hibernate.UserQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserQuestionService {
    private final UserQuestionDAOImpl hibernateUserQuestionDAO;

    @Autowired
    public UserQuestionService(UserQuestionDAOImpl hibernateUserQuestionDAO) {
        this.hibernateUserQuestionDAO = hibernateUserQuestionDAO;
    }

    @Transactional
    public void createUserQuestion(Integer quizID,
                                   Integer questionID,
                                   String userAnswer,
                                   Integer selectedOptionID,
                                   Boolean isShortQuestion) {
        UserQuestion userQuestion = UserQuestion.builder()
                .quizID(quizID)
                .questionID(questionID)
                .userAnswer(userAnswer)
                .selectedOptionID(selectedOptionID)
                .shortQuestion(isShortQuestion).build();
        hibernateUserQuestionDAO.insertUserQuestion(userQuestion);
    }

    @Transactional
    public void insertUserQuestion(UserQuestion userQuestion) {
        hibernateUserQuestionDAO.insertUserQuestion(userQuestion);
    }

    @Transactional
    public List<UserQuestion> getUserQuestionByQuizID(Integer quizID) {
        return hibernateUserQuestionDAO.findAllByQuizID(quizID);
    }

}
