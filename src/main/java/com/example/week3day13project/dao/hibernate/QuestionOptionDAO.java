package com.example.week3day13project.dao.hibernate;

import com.example.week3day13project.domain.hibernate.QuestionOption;

import java.util.List;
import java.util.Map;

public interface QuestionOptionDAO {
    List<QuestionOption> findAll();
    QuestionOption findByID(Integer optionID);
    Map<Integer, List<QuestionOption>> findAllAndGroupByQuestionID();
    void addOption(QuestionOption option);
}
