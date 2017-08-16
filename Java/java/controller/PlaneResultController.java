package controller;

import db.entity.Test;
import ui.form.PlaneResultData;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: PlaneResultController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Named
public class PlaneResultController implements Serializable {

    @Inject
    PlaneResultData planeResultData;

    public String show(Test test) {
        planeResultData.setTestId(test.getId());
        return "plane-test-result";
    }
}