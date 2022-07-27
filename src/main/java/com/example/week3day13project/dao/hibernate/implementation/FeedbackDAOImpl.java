package com.example.week3day13project.dao.hibernate.implementation;

import com.example.week3day13project.dao.hibernate.AbstractHibernateDAO;
import com.example.week3day13project.dao.hibernate.FeedbackDAO;
import com.example.week3day13project.domain.hibernate.Feedback;
import com.example.week3day13project.domain.hibernate.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class FeedbackDAOImpl extends AbstractHibernateDAO<Feedback> implements FeedbackDAO {

    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<User> userCR;
    CriteriaQuery<Feedback> feedbackCR;
    Root<User> userRoot;
    Root<Feedback> feedbackRoot;

    private void initializeFeedbackSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        userCR = cb.createQuery(User.class);
        userRoot = userCR.from(User.class);
        feedbackCR = cb.createQuery(Feedback.class);
        feedbackRoot = feedbackCR.from(Feedback.class);
    }

    @Autowired
    public FeedbackDAOImpl() {
        setClazz(Feedback.class);
    }

    @Override
    public List<Feedback> findAll() {
        initializeFeedbackSession();

        feedbackCR.select(feedbackRoot);
        Query<Feedback> query = session.createQuery(feedbackCR);
        return query.getResultList();
    }

    //Returns false if user has rated, otherwise true.
    @Override
    public boolean canFeedback(Integer UserID) {
        initializeFeedbackSession();

        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("userId"), UserID));
        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        return result.filter(user -> !user.isHasRated()).isPresent();
    }

    @Override
    public void uploadFeedback(Integer rate, String date, String content) {
        session = getCurrentSession();
        Feedback feedback = Feedback.builder().rate(rate).date(date).content(content).build();
        session.save(feedback);
    }
}
