package db.datatable;

import db.dao.ITestDAO;
import db.dao.ITestResultDAO;
import db.entity.Test;
import db.entity.TestResult;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

/**
 * Project: Airport
 * File: TestResultDatatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */

@ViewScoped
@Named
public class TestResultDatatable implements Serializable {

    @Inject
    ITestDAO iTestDAO;

    @Inject
    ITestResultDAO iTestResultDAO;

    Collection<TestResult> results;
    Test test;

    public Collection<TestResult> getResults(Test test) {
        if (results == null || this.test != test) {
            this.test = test;
            refresh();
        }
        return this.results;

    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void refresh() {
        this.results = iTestResultDAO.query(test);
    }

}
