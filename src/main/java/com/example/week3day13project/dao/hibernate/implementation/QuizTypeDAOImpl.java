package com.example.week3day13project.dao.hibernate.implementation;

import com.example.week3day13project.dao.hibernate.AbstractHibernateDAO;
import com.example.week3day13project.dao.hibernate.QuizTypeDAO;
import com.example.week3day13project.domain.hibernate.QuizType;
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
public class QuizTypeDAOImpl extends AbstractHibernateDAO<QuizType> implements QuizTypeDAO {

    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<QuizType> quizTypeCR;
    Root<QuizType> quizTypeRoot;

    @Autowired
    public QuizTypeDAOImpl() {
        setClazz(QuizType.class);
    }

    private void initializeUserSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        quizTypeCR = cb.createQuery(QuizType.class);
        quizTypeRoot = quizTypeCR.from(QuizType.class);
    }


    @Override
    public List<QuizType> getAll() {
        initializeUserSession();
        quizTypeCR.select(quizTypeRoot);
        Query<QuizType> query = session.createQuery(quizTypeCR);
        return query.getResultList();
    }

    @Override
    public QuizType findByQuizType(Integer quizTypeIndex) {
        initializeUserSession();
        quizTypeCR.select(quizTypeRoot);
        quizTypeCR.where(cb.equal(quizTypeRoot.get("quizTypeNumber"), quizTypeIndex));
        Query<QuizType> query = session.createQuery(quizTypeCR);
        Optional<QuizType> result = query.getResultList().stream().findAny();
        return result.orElse(null);
    }
}
