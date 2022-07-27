package com.example.week3day13project.dao.hibernate;

import com.example.week3day13project.domain.hibernate.Feedback;

import java.util.Date;
import java.util.List;

public interface FeedbackDAO {
    List<Feedback> findAll();
    boolean canFeedback(Integer userID);
    void uploadFeedback(Integer rate, String date, String content);
}
