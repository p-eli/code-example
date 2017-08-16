package db.datatable;

import db.dao.ITestDAO;
import db.dao.ITestResultDAO;
import db.entity.Plane;
import db.entity.Test;
import db.entity.TestResult;
import org.omnifaces.cdi.ViewScoped;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

/**
 * Project: Airport
 * File: TestDatatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */

@ViewScoped
@Named
public class TestDatatable implements Serializable {

    @Inject
    ITestDAO iTestDAO;

    @Inject
    ITestResultDAO iTestResultDAO;

    private Collection<Test> values;
    private Collection<Test> allValues;
    private Test value;

    public Collection<Test> getValues(Plane plane) {
        if (values == null) {
            refresh(plane);
        }
        return values;
    }

    public Collection<Test> getAllValues() {
        if (allValues == null) {
            refreshAll();
        }
        return allValues;
    }

    public Test getById(int id) {
        if (value == null || value.getId() != id) {

            value = iTestDAO.getById(id);
        }
        return value;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void merge(Test test, Collection<TestResult> res) {
        iTestDAO.merge(test);

        if (res != null) {
            for (TestResult result : res) {
                result.setTest(test);
                iTestResultDAO.merge(result);
            }
        }
    }

    public void refresh(Plane plane) {
        values = iTestDAO.readByPlane(plane);
    }

    public void refreshAll() {
        allValues = iTestDAO.readAll();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void persist(Test test, Collection<TestResult> res) {
        iTestDAO.persist(test);

        if (res != null) {
            for (TestResult result : res) {
                result.setTest(test);
                iTestResultDAO.persist(result);
            }
        }

    }
}
