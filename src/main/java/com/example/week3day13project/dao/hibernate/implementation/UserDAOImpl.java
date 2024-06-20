package com.example.week3day13project.dao.hibernate.implementation;

import com.example.week3day13project.dao.hibernate.AbstractHibernateDAO;
import com.example.week3day13project.dao.hibernate.UserDAO;
import com.example.week3day13project.domain.hibernate.User;
import com.example.week3day13project.security.PasswordHandler;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl extends AbstractHibernateDAO<User> implements UserDAO {

    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<User> userCR;
    Root<User> userRoot;

    public UserDAOImpl() {
        setClazz(User.class);
    }

    private void initializeUserSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        userCR = cb.createQuery(User.class);
        userRoot = userCR.from(User.class);
    }

    @Override
    public List<User> findAll() {
        initializeUserSession();

        userCR.select(userRoot);
        Query<User> query = session.createQuery(userCR);
        return query.getResultList();
    }

    public User findById(Integer UserID) {
        initializeUserSession();

        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("userId"), UserID));
        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        return result.orElse(null);
    }

    @Override
    public User findByName(String userName) {
        initializeUserSession();

        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("userName"), userName));
        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        return result.orElse(null);
    }

    @Override
    public void insertUser(User newUser) {
        session = getCurrentSession();
        session.save(newUser);
    }

    @Override
    public void setUserRated(Integer userID) {
        initializeUserSession();

        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("userId"), userID));
        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        if (!result.isPresent()) {
            System.out.println("User not found in system");
        } else {
            User user = result.get();
            user.setHasRated(true);
            session.update(user);
        }
    }

    @Nullable
    @Override
    public User validateLogin(String userName, String password) {
        initializeUserSession();

        Predicate userNameMatch = cb.equal(userRoot.get("userName"), userName);
        userCR.where(userNameMatch);

        //Lookup hash and salt value by userId
        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findFirst();

        if (result.isPresent()) {
            User userData = result.get();
            String saltValue = userData.getSalt();
            String hashValue = userData.getUserPW();
            boolean isValidUser = PasswordHandler.isValidUserPassword(password, hashValue, saltValue);
            if (isValidUser) {
                //System.out.println("Logged in");
                return userData;
            }
        } else {
            System.out.println("No user found");
        }
        //System.out.println("Failed");
        return null;
    }

    @Override
    public User getByEmail(String userEmail) {
        initializeUserSession();

        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("email"), userEmail));

        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        return result.orElse(null);
    }

    @Override
    public User getByUserName(String userName) {
        initializeUserSession();

        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("userName"), userName));

        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        return result.orElse(null);
    }

    @Override
    public void suspendUser(Integer userID) {
        initializeUserSession();

        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("userId"), userID));

        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        if (result.isPresent()) {
            User user = result.get();
            user.setSuspended(true);
        }
    }

    @Override
    public void unSuspendUser(Integer userID) {
        initializeUserSession();

        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("userId"), userID));
        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        if (result.isPresent()) {
            User user = result.get();
            user.setSuspended(false);
        }
    }
}
