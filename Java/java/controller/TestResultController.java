package controller;

import db.datatable.TestDatatable;
import db.entity.Plane;
import db.entity.Test;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

/**
 * Project: Airport
 * File: TestResultController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Named
@SessionScoped
public class TestResultController implements Serializable {

    @Inject
    TestDatatable testDatatable;

    private Plane plane;

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

}