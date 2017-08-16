package db.repository;

import db.dao.ITestTypeParameterDAO;
import db.entity.TestType;
import db.entity.TestTypeParameter;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


/**
 * Project: Airport
 * File: TestTypeParameterRepository.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Stateless
@Dependent
public class TestTypeParameterRepository implements ITestTypeParameterDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<TestTypeParameter> queryType(int typeId) {
        TypedQuery<TestTypeParameter> query = entityManager.createQuery("select e from TestTypeParameter e where e.type.id = :typeId", TestTypeParameter.class);
        query.setParameter("typeId", typeId);
        return query.getResultList();
    }

    public List<TestTypeParameter> queryFilter(TestType type, String checkingInterval, int min, int max) {
        throw new UnsupportedOperationException();
    }

    public TestTypeParameter find(Integer id) {
        return entityManager.find(TestTypeParameter.class, id);
    }

    public TestTypeParameter merge(TestTypeParameter testTypeParameter) {
        return entityManager.merge(testTypeParameter);
    }

    public void persist(TestTypeParameter testTypeParameter) {
        entityManager.persist(testTypeParameter);
    }

    public void remove(TestTypeParameter testTypeParameter) {
        TestTypeParameter attached = find(testTypeParameter.getId());
        entityManager.remove(entityManager.contains(attached) ? attached : entityManager.merge(attached));
    }

    public List<TestTypeParameter> queryAll() {
        TypedQuery<TestTypeParameter> query = entityManager.createQuery("select e from TestTypeParameter e", TestTypeParameter.class);
        return query.getResultList();
    }

}
