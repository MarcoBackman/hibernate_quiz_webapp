package com.example.week3day13project.service;

import com.example.week3day13project.dao.hibernate.QuizLogDAO;
import com.example.week3day13project.dao.hibernate.implementation.QuizLogDAOImpl;
import com.example.week3day13project.dao.hibernate.implementation.QuizTypeDAOImpl;
import com.example.week3day13project.dao.hibernate.implementation.UserDAOImpl;
import com.example.week3day13project.domain.hibernate.Quiz;
import com.example.week3day13project.domain.hibernate.QuizLog;
import com.example.week3day13project.domain.hibernate.QuizType;
import com.example.week3day13project.domain.hibernate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuizLogService {
    private final QuizLogDAOImpl hibernateQuizLogDAO;
    private final QuizTypeDAOImpl hibernateQuizTypeDAO;
    private final UserDAOImpl hibernateUserDAO;

    @Autowired
    public QuizLogService(QuizLogDAOImpl hibernateQuizLogDAO,
                          QuizTypeDAOImpl hibernateQuizTypeDAO,
                          UserDAOImpl hibernateUserDAO) {
        this.hibernateQuizLogDAO = hibernateQuizLogDAO;
        this.hibernateQuizTypeDAO = hibernateQuizTypeDAO;
        this.hibernateUserDAO = hibernateUserDAO;
    }

    @Transactional
    public void createQuizLog(Integer userID, String timeStart, String timeEnd, Integer quizID) {
        QuizLog quizLog = QuizLog.builder()
                .userID(userID)
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .quizID(quizID)
                .build();
        hibernateQuizLogDAO.insertQuizLog(quizLog);
    }

    @Transactional
    public List<QuizLog> getAllQuizLog() {
        return hibernateQuizLogDAO.findAll();
    }

    @Transactional
    public QuizLog getQuizLogByQuiz(Quiz quiz) {
        return hibernateQuizLogDAO.findByQuizID(quiz.getQuizID());
    }

    @Transactional
    public List<QuizLog> getQuizLogByUserName(Integer userName) {
        return hibernateQuizLogDAO.findByUserID(userName);
    }

    @Transactional
    public List<QuizLog> getQuizLogByUserID(Integer userID) {
        return hibernateQuizLogDAO.findByUserID(userID);
    }

    @Transactional
    public QuizType getTypeDescriptionByQuiz(Quiz quiz) {
        return hibernateQuizTypeDAO.findByQuizType(quiz.getQuizTypeIndex());
    }

    @Transactional
    public User getUserByQuizLog(QuizLog quizLog) {
        return hibernateUserDAO.findById(quizLog.getUserID());
    }

    @Transactional
    public List<QuizType> getAllQuizTypes() {
        return hibernateQuizTypeDAO.getAll();
    }

}
