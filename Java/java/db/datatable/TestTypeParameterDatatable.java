package db.datatable;

import db.dao.ITestTypeParameterDAO;
import db.entity.TestType;
import db.entity.TestTypeParameter;
import org.omnifaces.cdi.ViewScoped;
import ui.filter.TestTypeParameterFilter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Airport
 * File: TestTypeParameterDatatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ViewScoped
@Named
public class TestTypeParameterDatatable implements Serializable {

    @Inject
    ITestTypeParameterDAO iTestTypeParameterDAO;

    @Inject
    TestTypeParameterFilter testTypeParameterFilter;

    private List<TestTypeParameter> allValues;
    private List<TestTypeParameter> values;

    public List<TestTypeParameter> getFor(int typeId) {
        return iTestTypeParameterDAO.queryType(typeId);
    }

    public String getTestType() {
        List<TestTypeParameter> tmp;
        if ((values == null) && (testTypeParameterFilter.getTestTypeId() != -1)) {
            tmp = iTestTypeParameterDAO.queryType(testTypeParameterFilter.getTestTypeId());
        } else {
            tmp = values;
        }
        return tmp.get(0).getType().getName();
    }

    public List<TestType> getTestTypeAll() {
        if (allValues == null) {
            refreshAll();
        }
        List<TestType> testType = new ArrayList<TestType>();
        for (TestTypeParameter ttp : allValues) {
            TestType item = ttp.getType();
            if (!testType.contains(item)) {
                testType.add(item);
            }
        }
        return testType;
    }

    public List<String> getCheckingValueAll() {
        if (allValues == null) {
            refreshAll();
        }
        List<String> checkingValue = new ArrayList<String>();
        for (TestTypeParameter ttp : allValues) {
            String item = ttp.getCheckingValue();
            if (!checkingValue.contains(item)) {
                checkingValue.add(item);
            }
        }
        return checkingValue;
    }

    public List<Integer> getMinAll() {
        if (values == null) {
            refresh();
        }
        List<Integer> min = new ArrayList<Integer>();
        for (TestTypeParameter ttp : values) {
            int item = ttp.getMin();
            if (!min.contains(item)) {
                min.add(item);
            }
        }
        return min;
    }


    public List<Integer> getMaxAll() {
        if (values == null) {
            refresh();
        }

        List<Integer> max = new ArrayList<Integer>();
        for (TestTypeParameter ttp : values) {
            int item = ttp.getMax();
            if (!max.contains(item)) {
                max.add(item);
            }
        }
        return max;
    }

    public List<TestTypeParameter> getAllValues() {
        if (allValues == null) {
            refreshAll();
        }

        return allValues;
    }

    public List<TestTypeParameter> getValues() {
        if (values == null) {
            refresh();
        }
        return values;
    }


    public void refresh() {
        values = iTestTypeParameterDAO.queryType(testTypeParameterFilter.getTestTypeId());

    }

    public void refreshAll() {
        allValues = iTestTypeParameterDAO.queryAll();
    }

}
