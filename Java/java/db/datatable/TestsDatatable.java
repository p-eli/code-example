package db.datatable;

import db.dao.IPlaneDAO;
import db.dao.ITestDAO;
import db.entity.Plane;
import db.entity.Test;
import org.apache.commons.lang3.tuple.Pair;
import org.omnifaces.cdi.ViewScoped;
import ui.TestStatusEnum;
import ui.filter.TestFilter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Airport
 * File: TestsDatatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ViewScoped
@Named
public class TestsDatatable implements Serializable {

    @Inject
    IPlaneDAO iPlaneDAO;

    @Inject
    ITestDAO iTestDAO;

    @Inject
    TestFilter testFilter;


    private List<Test> values;
    private List<Test> valuesAll;


    private Plane plane = null;

    public Plane getPlane() {
        if ((plane == null) && (testFilter.getPlaneId() != -1)) {
            plane = iPlaneDAO.find(testFilter.getPlaneId());
        }
        return plane;
    }

    public List<Test> getValues() {
        if (values == null) {
            refresh();
        }
        return values;
    }

    public List<Test> getAllValues() {
        if (valuesAll == null) {
            refreshAll();
        }
        return valuesAll;
    }


    public List<TestStatusEnum> getStatusList() {
        List<TestStatusEnum> list = new ArrayList<TestStatusEnum>();
        list.add(TestStatusEnum.ACTUAL);
        list.add(TestStatusEnum.OUTDATE);
        list.add(TestStatusEnum.OLD);
        return list;
    }

    public List<Pair<Integer, String>> getTechnicians() {
        List<Pair<Integer, String>> technicians = new ArrayList<Pair<Integer, String>>();
        for (Test test : getAllValues()) {
            Pair<Integer, String> name = Pair.of(test.getTechnician().getId(), test.getTechnician().getFullName());
            if (!technicians.contains(name)) {
                technicians.add(name);
            }
        }
        return technicians;
    }


    public void refresh() {
        values = iTestDAO.filterTests(testFilter.getPlaneId(),
                testFilter.getDateMin(), testFilter.getDateMax(), testFilter.getStatus(), testFilter.getTechnician());
    }

    public void refreshAll() {
        valuesAll = new ArrayList<Test>(iTestDAO.readAll());
    }

}
