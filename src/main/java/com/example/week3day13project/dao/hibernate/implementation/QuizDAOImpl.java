package com.example.week3day13project.dao.hibernate.implementation;

import com.example.week3day13project.dao.hibernate.AbstractHibernateDAO;
import com.example.week3day13project.dao.hibernate.QuizDAO;
import com.example.week3day13project.domain.hibernate.Quiz;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class QuizDAOImpl extends AbstractHibernateDAO<Quiz> implements QuizDAO {

    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<Quiz> quizCR;
    Root<Quiz> quizRoot;

    public QuizDAOImpl() {
        setClazz(Quiz.class);
    }

    private void initializeQuizSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        quizCR = cb.createQuery(Quiz.class);
        quizRoot = quizCR.from(Quiz.class);
    }

    @Override
    public Quiz findByQuizID(Integer quizID) {

        initializeQuizSession();

        quizCR.select(quizRoot);
        quizCR.where(cb.equal(quizRoot.get("quizID"), quizID));
        Query<Quiz> query = session.createQuery(quizCR);
        Optional<Quiz> result = query.getResultList().stream().findAny();
        return result.orElse(null);
    }

    @Override
    //must return quiz_id
    public Integer createQuiz(Quiz quiz) {
        session = getCurrentSession();
        return (Integer) session.save(quiz);

    }

    @Override
    public List<Quiz> findQuizzesByTypeIndex(Integer typeIndex) {
        initializeQuizSession();

        quizCR.select(quizRoot);
        quizCR.where(cb.equal(quizRoot.get("quizTypeIndex"), typeIndex));
        Query<Quiz> query = session.createQuery(quizCR);
        return query.getResultList();
    }
}
