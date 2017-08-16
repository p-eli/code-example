package db.datatable;

import db.dao.ITestTypeDAO;
import db.entity.TestType;
import org.omnifaces.cdi.ViewScoped;
import ui.filter.TestTypeFilter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Airport
 * File: TestTypeDatatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ViewScoped
@Named
public class TestTypeDatatable implements Serializable {

    @Inject
    ITestTypeDAO iTestTypeDAO;

    @Inject
    TestTypeFilter testTypeFilter;

    private List<TestType> allValues;
    private List<TestType> values;

    public List<Integer> getCheckingIntervalAll() {
        if (allValues == null) {
            refreshAll();
        }
        List<Integer> checkingInterval = new ArrayList<Integer>();
        for (TestType pl : allValues) {
            int item = pl.getCheckingInterval();
            if (!checkingInterval.contains(item)) {
                checkingInterval.add(item);
            }
        }
        return checkingInterval;
    }

    public List<Integer> getCheckingInterval() {
        if (values == null) {
            refresh();
        }
        List<Integer> checkingInterval = new ArrayList<Integer>();
        for (TestType pl : values) {
            int item = pl.getCheckingInterval();
            if (!checkingInterval.contains(item)) {
                checkingInterval.add(item);
            }
        }
        return checkingInterval;
    }


    public List<TestType> getAllValues() {
        if (allValues == null) {
            refreshAll();
        }

        return allValues;
    }

    public List<TestType> getValues() {
        if (values == null) {
            refresh();
        }
        return values;
    }


    public void refresh() {
        values = iTestTypeDAO.queryFilter(testTypeFilter.getCheckingInterval());

    }

    public void refreshAll() {
        allValues = iTestTypeDAO.queryAll();
    }


}
