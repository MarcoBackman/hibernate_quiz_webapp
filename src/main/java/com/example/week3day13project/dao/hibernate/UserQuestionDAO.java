package com.example.week3day13project.dao.hibernate;

import com.example.week3day13project.domain.hibernate.UserQuestion;

import java.util.List;

public interface UserQuestionDAO {
    List<UserQuestion> findAll();
    List<UserQuestion> findAllByQuizID(Integer quizID);
    void insertUserQuestion(UserQuestion userQuestion);
}
