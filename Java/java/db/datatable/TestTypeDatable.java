package db.datatable;

import db.dao.ITestTypeDAO;
import db.entity.TestType;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

/**
 * Project: Airport
 * File: TestTypeDatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ViewScoped
@Named
public class TestTypeDatable implements Serializable {

    @Inject
    ITestTypeDAO iTestTypeDao;

    private Collection<TestType> values;

    public Collection<TestType> getValues() {
        if (values == null) {
            refresh();
        }
        return values;
    }


    public void refresh() {
        values = iTestTypeDao.queryAll();
    }
}
