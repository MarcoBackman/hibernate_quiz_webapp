package com.example.week3day13project.dao.jdbc;

import com.example.week3day13project.domain.jdbc.Feedback;

import java.util.List;

public interface FeedbackDAOScheme {
    List<Feedback> findAll();
    boolean checkFeedbackAvailability(String user_id);
    boolean uploadFeedback(String rate, String content);
}
