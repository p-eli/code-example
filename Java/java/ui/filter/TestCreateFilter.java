package ui.filter;


import db.entity.Plane;
import db.entity.PlaneModel;
import db.entity.TestType;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestCreateFilter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class TestCreateFilter implements Serializable {

    private PlaneModel model = null;
    private Plane plane = null;

    public PlaneModel getModel() {
        return model;
    }

    public void setModel(PlaneModel model) {
        this.model = model;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    private TestType testType = null;
}
