package controller;

import db.datatable.TestDatatable;
import ui.filter.TestFilter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Named
@SessionScoped
public class TestController implements Serializable {

    @Inject
    TestDatatable testDatatable;

    @Inject
    TestFilter testFilter;

    public String showTests() {
        testFilter.setPlaneId(-1);
        testFilter.clear();
        return "list-tests";
    }

    public String showTests(int id) {
        testFilter.clear();
        testFilter.setPlaneId(id);
        return "list-tests";
    }

    public String clearFilter() {
        testFilter.clear();
        return "list-tests";
    }
}