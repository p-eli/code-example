package db.datatable;

import db.dao.IPlaneModelDAO;
import db.entity.PlaneModel;
import org.omnifaces.cdi.ViewScoped;
import ui.filter.PlaneModelFilter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Airport
 * File: PlaneModelDatatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ViewScoped
@Named
public class PlaneModelDatatable implements Serializable {

    @Inject
    IPlaneModelDAO iPlaneModelDAO;

    @Inject
    PlaneModelFilter planeModelFilter;

    private List<PlaneModel> allValues;
    private List<PlaneModel> values;

    public List<String> getManufacturerAll() {
        if (allValues == null) {
            refreshAll();
        }
        List<String> manufacturer = new ArrayList<String>();
        for (PlaneModel pl : allValues) {
            String item = pl.getManufacturer();
            if (!manufacturer.contains(item)) {
                manufacturer.add(item);
            }
        }
        return manufacturer;
    }

    public List<String> getManufacturer() {
        if (values == null) {
            refresh();
        }
        List<String> manufacturer = new ArrayList<String>();
        for (PlaneModel pl : values) {
            String item = pl.getManufacturer();
            if (!manufacturer.contains(item)) {
                manufacturer.add(item);
            }
        }
        return manufacturer;
    }


    public List<String> getType() {
        if (values == null) {
            refresh();
        }

        List<String> type = new ArrayList<String>();
        for (PlaneModel pl : values) {
            String item = pl.getType();
            if (!type.contains(item)) {
                type.add(item);
            }
        }
        return type;
    }

    public List<Integer> getCapacity() {
        if (values == null) {
            refresh();
        }
        List<Integer> capacity = new ArrayList<Integer>();
        for (PlaneModel pl : values) {
            Integer item = pl.getCapacity();
            if (!capacity.contains(item)) {
                capacity.add(item);
            }
        }
        return capacity;
    }

    public List<Integer> getEnginePower() {
        if (values == null) {
            refresh();
        }
        List<Integer> enginePower = new ArrayList<Integer>();
        for (PlaneModel pl : values) {
            Integer item = pl.getEnginePower();
            if (!enginePower.contains(item)) {
                enginePower.add(item);
            }
        }
        return enginePower;
    }

    public List<PlaneModel> getAllValues() {
        if (allValues == null) {
            refreshAll();
        }

        return allValues;
    }

    public List<PlaneModel> getValues() {
        if (values == null) {
            refresh();
        }
        return values;
    }


    public void refresh() {
        values = iPlaneModelDAO.queryFilter(planeModelFilter.getManufacturer(), planeModelFilter.getType(), planeModelFilter.getCapacity(), planeModelFilter.getEnginePower());

    }

    public void refreshAll() {
        allValues = iPlaneModelDAO.queryAll();
    }


}
