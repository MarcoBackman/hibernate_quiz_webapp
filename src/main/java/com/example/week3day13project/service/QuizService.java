package com.example.week3day13project.service;

import com.example.week3day13project.dao.hibernate.implementation.QuizDAOImpl;
import com.example.week3day13project.dao.hibernate.implementation.QuizTypeDAOImpl;
import com.example.week3day13project.domain.hibernate.Quiz;
import com.example.week3day13project.domain.hibernate.QuizType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizService {
    private final QuizDAOImpl hibernateQuizDAO;
    private final QuizTypeDAOImpl hibernateQuizTypeDAO;

    @Autowired
    public QuizService(QuizDAOImpl hibernateQuizDAO, QuizTypeDAOImpl hibernateQuizTypeDAO) {
        this.hibernateQuizDAO = hibernateQuizDAO;
        this.hibernateQuizTypeDAO = hibernateQuizTypeDAO;
    }

    @Transactional
    public List<QuizType> getAllQuizTypes() {
        return hibernateQuizTypeDAO.getAll();
    }

    @Transactional
    public QuizType getQuizTypesByTypeNumber(Integer index) {
        return hibernateQuizTypeDAO.findByQuizType(index);
    }


    //Add score to quiz log
    /**
     * This creates a quiz object and store it into DB
     * @return Integer quiz_id
     */
    @Transactional
    public int createQuizObject(Integer quizTypeIndex, Integer score) {
        Quiz quiz = Quiz.builder().quizTypeIndex(quizTypeIndex).score(score).build();
        return hibernateQuizDAO.createQuiz(quiz);
    }

    //This will only return one Quiz set since quizID is unique
    @Transactional
    public Quiz getQuizByID(Integer quizID) {
        return hibernateQuizDAO.findByQuizID(quizID);
    }

    @Transactional
    public List<Quiz> getQuizByType(Integer quizTypeIndex) {
        return hibernateQuizDAO.findQuizzesByTypeIndex(quizTypeIndex);
    }
}
