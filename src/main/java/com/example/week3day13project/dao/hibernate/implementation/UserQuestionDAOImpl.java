package com.example.week3day13project.dao.hibernate.implementation;

import com.example.week3day13project.dao.hibernate.AbstractHibernateDAO;
import com.example.week3day13project.dao.hibernate.UserQuestionDAO;
import com.example.week3day13project.domain.hibernate.UserQuestion;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserQuestionDAOImpl extends AbstractHibernateDAO<UserQuestion> implements UserQuestionDAO {
    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<UserQuestion> userQuestionCR;
    Root<UserQuestion> userQuestionRoot;

    public UserQuestionDAOImpl() {
        setClazz(UserQuestion.class);
    }

    private void initializeUserQuestionSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        userQuestionCR = cb.createQuery(UserQuestion.class);
        userQuestionRoot = userQuestionCR.from(UserQuestion.class);
    }

    @Override
    public List<UserQuestion> findAll() {
        initializeUserQuestionSession();
        userQuestionCR.select(userQuestionRoot);
        Query<UserQuestion> query = session.createQuery(userQuestionCR);
        return query.getResultList();
    }

    @Override
    public List<UserQuestion> findAllByQuizID(Integer quizID) {
        initializeUserQuestionSession();
        userQuestionCR.select(userQuestionRoot);
        userQuestionCR.where(cb.equal(userQuestionRoot.get("quizID"), quizID));
        Query<UserQuestion> query = session.createQuery(userQuestionCR);
        return query.getResultList();
    }

    @Override
    public void insertUserQuestion(UserQuestion userQuestion) {
        session = getCurrentSession();
        session.save(userQuestion);
    }
}
