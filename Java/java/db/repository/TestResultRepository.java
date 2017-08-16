package db.repository;

import db.dao.ITestResultDAO;
import db.entity.Test;
import db.entity.TestResult;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

/**
 * Project: Airport
 * File: TestResultRepository.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Stateless
@Dependent
public class TestResultRepository implements ITestResultDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TestResult find(Integer id) {
        return entityManager.find(TestResult.class, id);
    }

    public TestResult merge(TestResult testResult) {
        return entityManager.merge(testResult);
    }

    public void persist(TestResult testResult) {
        entityManager.persist(testResult);
    }

    public void remove(TestResult testResult) {
        TestResult attached = find(testResult.getId());
        entityManager.remove(entityManager.contains(attached) ? attached : entityManager.merge(attached));
    }

    public List<TestResult> queryAll() {
        TypedQuery<TestResult> query = entityManager.createQuery("select e from TestResult e", TestResult.class);
        return query.getResultList();
    }

    public Collection<TestResult> query(Test test) {
        TypedQuery<TestResult> query = entityManager.createQuery("select e from TestResult e where e.test = :Test", TestResult.class);
        query.setParameter("Test", test);
        return query.getResultList();
    }

}
