package com.example.week3day13project.dao.hibernate;

import com.example.week3day13project.domain.hibernate.QuizType;

import java.util.List;

public interface QuizTypeDAO {
    List<QuizType> getAll();
    QuizType findByQuizType(Integer quizTypeIndex);
}
