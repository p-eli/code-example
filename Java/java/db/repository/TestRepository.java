package db.repository;

import db.dao.ITestDAO;
import db.entity.Plane;
import db.entity.PlaneModel;
import db.entity.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ui.TestStatusEnum;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Project: Airport
 * File: TestRepository.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Stateless
@Dependent
public class TestRepository implements ITestDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void persist(Test test) {
        entityManager.persist(test);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void merge(Test test) {
        entityManager.merge(test);
    }

    public Test getById(int id) {
        TypedQuery<Test> query = entityManager.createQuery("select e from Test e where e.id = :id", Test.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public void delete(Test test) {
        throw new NotImplementedException();
    }

    public Collection<Test> readByPlane(Plane plane) {
        TypedQuery<Test> query = entityManager.createQuery("select e from Test e where e.plane = :plane", Test.class);
        query.setParameter("plane", plane);
        return query.getResultList();
    }

    public Collection<Test> readAll() {
        TypedQuery<Test> query = entityManager.createQuery("select e from Test e", Test.class);
        return query.getResultList();
    }

    public Test actualByPlane(Plane plane) {
        try {
            TypedQuery<Test> query = entityManager.createQuery(
                    "                    SELECT " +
                            "                            (SELECT t FROM Test t where t.id=max(test.id))" +
                            "            FROM Test test, TestType test_type " +
                            "            WHERE test_type = test.type and test.plane = :plane " +
                            "and not exists (select f from Test f where f.plane = :plane and test.type = f.type and f.date > test.date) " +
                            "            GROUP BY test.type " +
                            "            order by (test_type.checkingInterval - timestampdiff(DAY, max(test.date), NOW())) asc"
                    , Test.class);
            query.setMaxResults(1);
            query.setParameter("plane", plane);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Test> filterTests(int planeId, Date dateMin, Date dateMax, TestStatusEnum status, int technicianId) {
        String where_item = "where ";
        if (planeId != -1) {
            where_item += "e.plane.id = :planeId";
        }
        if (dateMin != null) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "e.date >= :dateMin";
        }
        if (dateMax != null) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "e.date <= :dateMax";
        }
        if (status == TestStatusEnum.ACTUAL) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "TIMESTAMPDIFF(DAY, e.date, NOW()) < e.type.checkingInterval";
        } else if (status == TestStatusEnum.OUTDATE) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "TIMESTAMPDIFF(DAY, e.date, NOW()) > e.type.checkingInterval " +
                    "and not exists (select f from Test f where e.type = f.type and f.date > e.date) ";
        } else if (status == TestStatusEnum.OLD) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "TIMESTAMPDIFF(DAY, e.date, NOW()) > e.type.checkingInterval";
        }
        if (technicianId != -1) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "e.technician.id = :technicianId";
        }

        TypedQuery<Test> query;
        if (where_item != "where ") {
            query = entityManager
                    .createQuery("select e from Test e " + where_item, Test.class);
        } else {
            query = entityManager
                    .createQuery("select e from Test e ", Test.class);
        }


        if (planeId != -1) {
            query.setParameter("planeId", planeId);
        }
        if (dateMin != null) {
            query.setParameter("dateMin", dateMin);
        }
        if (dateMax != null) {
            query.setParameter("dateMax", dateMax);
        }
        if (technicianId != -1) {
            query.setParameter("technicianId", technicianId);
        }


        return query.getResultList();


    }
}
