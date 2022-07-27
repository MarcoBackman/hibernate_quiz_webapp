package com.example.week3day13project.dao.hibernate;

import com.example.week3day13project.domain.hibernate.Quiz;

import java.util.List;

public interface QuizDAO {
    Quiz findByQuizID(Integer quizID);
    Integer createQuiz(Quiz quiz);
    List<Quiz> findQuizzesByTypeIndex(Integer typeIndex);
}
