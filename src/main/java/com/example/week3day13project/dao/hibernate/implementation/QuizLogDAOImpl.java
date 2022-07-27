package com.example.week3day13project.dao.hibernate.implementation;

import com.example.week3day13project.dao.hibernate.AbstractHibernateDAO;
import com.example.week3day13project.dao.hibernate.QuizLogDAO;
import com.example.week3day13project.domain.hibernate.Quiz;
import com.example.week3day13project.domain.hibernate.QuizLog;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class QuizLogDAOImpl extends AbstractHibernateDAO<QuizLog> implements QuizLogDAO {

    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<QuizLog> quizLogCR;
    Root<QuizLog> quizLogRoot;

    @Autowired
    public QuizLogDAOImpl() {
        setClazz(QuizLog.class);
    }

    private void initializeQuizSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        quizLogCR = cb.createQuery(QuizLog.class);
        quizLogRoot = quizLogCR.from(QuizLog.class);
    }

    @Override
    public List<QuizLog> findAll() {
        initializeQuizSession();
        quizLogCR.select(quizLogRoot);
        Query<QuizLog> query = session.createQuery(quizLogCR);
        return query.getResultList();
    }

    @Override
    public QuizLog findByQuizID(Integer quizID) {
        initializeQuizSession();
        quizLogCR.select(quizLogRoot);
        quizLogCR.where(cb.equal(quizLogRoot.get("quizID"), quizID));
        Query<QuizLog> query = session.createQuery(quizLogCR);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<QuizLog> findByUserID(Integer userID) {
        initializeQuizSession();
        quizLogCR.select(quizLogRoot);
        quizLogCR.where(cb.equal(quizLogRoot.get("userID"), userID));
        Query<QuizLog> query = session.createQuery(quizLogCR);
        return query.getResultList();
    }

    @Override
    public void insertQuizLog(QuizLog quizLog) {
        initializeQuizSession();
        session.save(quizLog);
    }
}
