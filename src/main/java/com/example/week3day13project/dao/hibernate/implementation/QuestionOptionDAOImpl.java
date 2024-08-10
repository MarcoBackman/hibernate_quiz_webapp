package com.example.week3day13project.dao.hibernate.implementation;

import com.example.week3day13project.dao.hibernate.AbstractHibernateDAO;
import com.example.week3day13project.dao.hibernate.QuestionOptionDAO;
import com.example.week3day13project.domain.hibernate.Question;
import com.example.week3day13project.domain.hibernate.QuestionOption;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionOptionDAOImpl extends AbstractHibernateDAO<QuestionOption> implements QuestionOptionDAO {
    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<QuestionOption> optionCR;
    Root<QuestionOption> optionRoot;

    public QuestionOptionDAOImpl() {
        setClazz(QuestionOption.class);
    }

    private void initializeOptionSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        optionCR = cb.createQuery(QuestionOption.class);
        optionRoot = optionCR.from(QuestionOption.class);
    }

    @Override
    public List<QuestionOption> findAll() {
        initializeOptionSession();
        optionCR.select(optionRoot);
        Query<QuestionOption> query = session.createQuery(optionCR);
        session.clear();
        return query.getResultList();
    }

    @Override
    public QuestionOption findByID(Integer optionID) {
        initializeOptionSession();
        optionCR.select(optionRoot);
        optionCR.where(cb.equal(optionRoot.get("optionID"), optionID));
        Query<QuestionOption> query = session.createQuery(optionCR);
        session.flush();
        session.clear();
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Map<Integer, List<QuestionOption>> findAllAndGroupByQuestionID() {
        initializeOptionSession();

        CriteriaQuery<Question> questionCR = cb.createQuery(Question.class);
        Root<Question> questionRoot = questionCR.from(Question.class);

        questionCR.select(questionRoot);
        Query<Question> query = session.createQuery(questionCR);
        List<Question> questions = query.getResultList();

        Map<Integer, List<QuestionOption>> group = new HashMap<>();

        for (Question question: questions) {
            group.put(question.getQuestionID(), question.getOptions());
        }
        return group;
    }

    @Override
    public void addOption(QuestionOption option) {
        session = getCurrentSession();
        session.save(option);
    }

    @Override
    public void deleteAllOptions(Integer questionID) {
        initializeOptionSession();
        optionCR.select(optionRoot);
        optionCR.where(cb.equal(optionRoot.get("questionID"), questionID));
        Query<QuestionOption> query = session.createQuery(optionCR);
        List<QuestionOption> options = query.getResultList();
        options.forEach(option -> session.delete(option));
    }

    @Override
    public List<QuestionOption> getOptionsByQuestionID(Integer questionID) {
        initializeOptionSession();
        optionCR.select(optionRoot);
        optionCR.where(cb.equal(optionRoot.get("questionID"), questionID));
        Query<QuestionOption> query = session.createQuery(optionCR);
        return query.getResultList();
    }
}
