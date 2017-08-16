package db.repository;

import auth.PasswordHasher;
import db.dao.IUserDAO;
import db.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Project: Airport
 * File: UserRepository.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Stateless
public class UserRepository implements IUserDAO {

    @PersistenceContext
    EntityManager entityManager;

    public User get(String login, String password) {
        try {
            TypedQuery<User> q = entityManager.createQuery(
                    "SELECT c FROM User c WHERE c.login = :login",
                    User.class
            )
                    .setMaxResults(1)
                    .setParameter("login", login);
            User u = q.getSingleResult();
            String databaseHash = u.getPassword();
            String inputHash = PasswordHasher.Hash(login, password);
            if (databaseHash.equals(inputHash))
                return u;
        } catch (NoResultException ex) {
            ;
        }
        return null;
    }

    public List<User> queryAll() {
        TypedQuery<User> query = entityManager.createQuery("select e from User e", User.class);
        return query.getResultList();
    }

    public User find(Integer id) {
        return entityManager.find(User.class, id);
    }

    public User merge(User user) {
        if (user.getPassword() == null) {
            User u = find(user.getId());
            user.setHash(u.getPassword());
        }
        return entityManager.merge(user);
    }

    public void persist(User user) {
        entityManager.persist(user);
    }

    public void remove(User user) {
        User attached = find(user.getId());
        entityManager.remove(attached);
    }

    public User findByLogin(String login) {
        try {
            TypedQuery<User> query = entityManager.createQuery("select e from User e where e.login = :login", User.class);
            query.setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}