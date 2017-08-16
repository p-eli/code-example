package db.repository;

import db.dao.ITestTypeDAO;
import db.entity.TestType;
import db.entity.TestTypeParameter;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Project: Airport
 * File: TestTypeRepository.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Stateless
@Dependent
public class TestTypeRepository implements ITestTypeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public TestType find(Integer id) {
        return entityManager.find(TestType.class, id);
    }

    public TestType merge(TestType testType) {
        return entityManager.merge(testType);
    }

    public void persist(TestType testType) {
        entityManager.persist(testType);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(TestType testType) {
        TestType attached = find(testType.getId());
        TestType toRemove = entityManager.contains(attached) ? attached : entityManager.merge(attached);

        TypedQuery<TestTypeParameter> query = entityManager.createQuery("select e from TestTypeParameter e where e.type.id = :typeId", TestTypeParameter.class);
        query.setParameter("typeId", toRemove.getId());
        for (TestTypeParameter param :query.getResultList()) {
            entityManager.remove(param);
        }
        entityManager.remove(toRemove);
    }

    public List<TestType> queryAll() {
        TypedQuery<TestType> query = entityManager.createQuery("select e from TestType e", TestType.class);
        return query.getResultList();
    }

    public List<TestType> queryFilter(int checkingInterval) {
        String where_item = "where ";
        if (checkingInterval != -1) {
            where_item += "e.checkingInterval = :checkingInterval";
        }

        TypedQuery<TestType> query;
        if (where_item != "where ") {
            query = entityManager
                    .createQuery("select e from TestType e " + where_item, TestType.class);
        } else {
            query = entityManager
                    .createQuery("select e from TestType e ", TestType.class);
        }

        if (checkingInterval != -1) {
            query.setParameter("checkingInterval", checkingInterval);
        }

        return query.getResultList();
    }
}
