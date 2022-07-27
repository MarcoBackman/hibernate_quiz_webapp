package com.example.week3day13project.service;

import com.example.week3day13project.dao.hibernate.implementation.UserDAOImpl;
import com.example.week3day13project.dao.hibernate.implementation.FeedbackDAOImpl;
import com.example.week3day13project.domain.hibernate.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackDAOImpl hibernateFeedbackDAO;
    private final UserDAOImpl hibernateUserDAO;

    @Autowired
    public FeedbackService(FeedbackDAOImpl hibernateFeedbackDAO,
                           UserDAOImpl hibernateUserDAO) {
        this.hibernateFeedbackDAO = hibernateFeedbackDAO;
        this.hibernateUserDAO = hibernateUserDAO;
    }

    @Transactional
    public boolean allowedToFeedback(Integer user_id) {
        return hibernateFeedbackDAO.canFeedback(user_id);
    }

    @Transactional
    public void leaveFeedBack(Integer rate, String date, String content) {
        hibernateFeedbackDAO.uploadFeedback(rate, date, content);
    }

    @Transactional
    public void updateUserFeedbackStatus(Integer user_id) {
        hibernateUserDAO.setUserRated(user_id);
    }

    @Transactional
    public List<Feedback> getAllFeedback() {
        return hibernateFeedbackDAO.findAll();
    }
}
