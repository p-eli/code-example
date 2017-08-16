package db.datatable;

import db.dao.IPlaneDAO;
import db.dao.IPlaneModelDAO;
import db.dao.ITestDAO;
import db.entity.Plane;
import db.entity.PlaneModel;
import db.entity.Test;
import org.apache.commons.lang3.tuple.Pair;
import org.omnifaces.cdi.ViewScoped;
import ui.TestStatus;
import ui.filter.PlaneFilter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Project: Airport
 * File: PlaneDatatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ViewScoped
@Named
public class PlaneDatatable implements Serializable {

    @Inject
    IPlaneDAO iPlaneDAO;

    @Inject
    IPlaneModelDAO iPlaneModelDAO;

    @Inject
    ITestDAO iTestDAO;

    @Inject
    PlaneFilter planeFilter;


    private List<Plane> values;
    private List<Plane> allValues;

    private List<Pair<Plane, TestStatus>> outdate;

    public String getPlaneType() {
        if ((values == null) && (planeFilter.getPlaneTypeId() != -1)) {
            return iPlaneModelDAO.find(planeFilter.getPlaneTypeId()).getType();
        } else if (values != null) {
            return values.get(0).getModel().getType();
        } else {
            return new String("");
        }

    }


    public Collection<Pair<Integer, String>> getAllModels() {

        Set<Pair<Integer, String>> set = new HashSet<Pair<Integer, String>>();
        for (Plane plane : getAllValues()) {
            PlaneModel m = plane.getModel();
            set.add(Pair.of(m.getId(), m.getType()));
        }
        return set;
    }

    public List<Pair<Plane, TestStatus>> getOutdate() {
        if (outdate == null) {
            refresh();
        }
        return outdate;
    }

    public List<Plane> getValues() {
        if (values == null) {
            refresh();
        }
        return values;
    }

    public List<Plane> getAllValues() {
        if (allValues == null) {
            refreshAll();
        }
        return allValues;
    }

    public void refreshAll() {
        allValues = iPlaneDAO.queryAll();
    }

    public void refresh() {

        Calendar cal = Calendar.getInstance();
        values = iPlaneDAO.queryFilter(planeFilter.getPlaneTypeId(),
                planeFilter.getDataOfManufactureMin(), planeFilter.getDataOfManufactureMax(),
                planeFilter.getFlyingHoursMin(), planeFilter.getFlyingHoursMax());
        outdate = new ArrayList<Pair<Plane, TestStatus>>();
        for (Plane plane : values) {
            TestStatus stat = new TestStatus();
            Test tests = iTestDAO.actualByPlane(plane);
            if (tests != null && tests.getType() != null) {
                cal.setTime(tests.getDate());
                cal.add(Calendar.DATE, tests.getType().getCheckingInterval());
                stat.setDate(new Date(cal.getTimeInMillis()));
            }
            outdate.add(Pair.of(plane, stat));
        }
    }

}
