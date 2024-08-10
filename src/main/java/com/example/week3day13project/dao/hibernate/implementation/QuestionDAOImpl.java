package com.example.week3day13project.dao.hibernate.implementation;

import com.example.week3day13project.dao.hibernate.AbstractHibernateDAO;
import com.example.week3day13project.dao.hibernate.QuestionDAO;
import com.example.week3day13project.domain.hibernate.Question;
import com.example.week3day13project.domain.hibernate.QuestionOption;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuestionDAOImpl extends AbstractHibernateDAO<Question> implements QuestionDAO {
    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<Question> questionCR;
    Root<Question> questionRoot;

    public QuestionDAOImpl() {
        setClazz(Question.class);
    }

    private void initializeQuestionSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        questionCR = cb.createQuery(Question.class);
        questionRoot = questionCR.from(Question.class);
    }

    @Override
    public List<Question> findAll() {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        Query<Question> query = session.createQuery(questionCR);
        return query.getResultList();
    }

    public List<Question> findAllByType(Integer quizType){
        initializeQuestionSession();
        questionCR.select(questionRoot);
        questionCR.where(cb.equal(questionRoot.get("quizType"), quizType));
        Query<Question> query = session.createQuery(questionCR);
        return query.getResultList();
    }

    public Integer getQuizCountByType(Integer quizType){
        initializeQuestionSession();
        Query<?> query = session.createQuery(
                "select count(*) from Question where quizType = :quizType");
        query.setParameter("quizType", quizType);
        return (Integer) query.uniqueResult();
    }

    @Override
    public Question findByQuestionID(Integer questionID) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        questionCR.where(cb.equal(questionRoot.get("questionID"), questionID));
        Query<Question> query = session.createQuery(questionCR);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Question> findAllMultipleQuestionByType(Integer typeIndex) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        Predicate quizTypeMatch
                = cb.equal(questionRoot.get("quizType"), typeIndex);

        Predicate noShortQuestion
                = cb.equal(questionRoot.get("shortQuestion"), false);

        Predicate finalPredicate
                = cb.and(quizTypeMatch, noShortQuestion);
        questionCR.where(finalPredicate);
        Query<Question> query = session.createQuery(questionCR);
        return query.getResultList();
    }

    @Override
    public List<Question> findAllShortQuestionByType(Integer typeIndex) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        Predicate quizTypeMatch
                = cb.equal(questionRoot.get("quizType"), typeIndex);

        Predicate noShortQuestion
                = cb.equal(questionRoot.get("shortQuestion"), true);

        Predicate finalPredicate
                = cb.and(quizTypeMatch, noShortQuestion);
        questionCR.where(finalPredicate);
        Query<Question> query = session.createQuery(questionCR);
        return query.getResultList();
    }

    @Override
    public Integer addQuestion(Question question) {
        session = getCurrentSession();
        return (Integer)session.save(question);
    }

    @Override
    public void updateQuestionContent(Integer questionID, String content) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        questionCR.where(cb.equal(questionRoot.get("questionID"), questionID));
        Query<Question> query = session.createQuery(questionCR);
        Question question = query.getResultList().stream().findFirst().orElse(null);
        if (question != null) {
            question.setQuestionContent(content);
            session.update(question);
        }
    }

    @Override
    public void deleteQuestion(Integer questionID) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        questionCR.where(cb.equal(questionRoot.get("questionID"), questionID));
        Query<Question> query = session.createQuery(questionCR);
        Question question = query.getResultList().stream().findFirst().orElse(null);
        if (question != null) {
            question.setActive(false);
            session.delete(question);
        }
    }

    @Override
    public void disableQuestion(Integer questionID) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        questionCR.where(cb.equal(questionRoot.get("questionID"), questionID));
        Query<Question> query = session.createQuery(questionCR);
        Question question = query.getResultList().stream().findFirst().orElse(null);
        if (question != null) {
            question.setActive(false);
            session.update(question);
        }
    }

    @Override
    public void activateQuestion(Integer questionID) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        questionCR.where(cb.equal(questionRoot.get("questionID"), questionID));
        Query<Question> query = session.createQuery(questionCR);
        Question question = query.getResultList().stream().findFirst().orElse(null);
        if (question != null) {
            question.setActive(true);
            session.update(question);
        }
    }

    @Override
    public boolean isShortQuestion(Integer questionID) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        questionCR.where(cb.equal(questionRoot.get("questionID"), questionID));
        Query<Question> query = session.createQuery(questionCR);
        Question question = query.getResultList().stream().findFirst().orElse(null);
        if (question != null) {
            return question.isShortQuestion();
        } else {
            System.out.println("Question is null! - isShortQuestion() in QuestionDAO");
        }
        return false;
    }

    @Override
    public LinkedHashMap<Question, List<QuestionOption>> getTenMultipleQuestionsByQuizType(Integer typeIndex) {
        initializeQuestionSession();
        LinkedHashMap<Question, List<QuestionOption>> questionSet = new LinkedHashMap<>();
        Query<Question> query = session.createNativeQuery(
                "SELECT * FROM Question WHERE quiz_type = :typeIndex AND is_short_question = FALSE AND is_active = TRUE ORDER BY RAND() LIMIT 10",
                Question.class);
        query.setParameter("typeIndex", typeIndex);

        List<Question> questions = query.getResultList();
        questions.forEach(question -> {
            questionSet.put(question, question.getOptions());
        });
        return questionSet;
    }

    @Override
    public List<Question> getTwoShortQuestionsByQuizType(Integer typeIndex) {
        initializeQuestionSession();
        questionCR.select(questionRoot);
        Predicate quizTypeMatch
                = cb.equal(questionRoot.get("quizType"), typeIndex);

        Predicate noShortQuestion
                = cb.equal(questionRoot.get("shortQuestion"), true);

        Predicate active
                = cb.equal(questionRoot.get("active"), true);

        Predicate firstPredicate
                = cb.and(quizTypeMatch, noShortQuestion);

        Predicate finalPredicate
                = cb.and(firstPredicate, active);

        questionCR.where(finalPredicate);
        Query<Question> query = session.createQuery(questionCR);
        List<Question> questions = query.getResultList();

        Collections.shuffle(questions);
        return questions.stream().limit(2).collect(Collectors.toList());
    }
}
