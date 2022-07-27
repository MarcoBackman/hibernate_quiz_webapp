package com.example.week3day13project.dao.hibernate;

import com.example.week3day13project.domain.hibernate.QuizLog;

import java.util.List;

public interface QuizLogDAO {
    List<QuizLog> findAll();
    QuizLog findByQuizID(Integer quizID);
    List<QuizLog> findByUserID(Integer userID);
    void insertQuizLog(QuizLog quizLog);
}
