package ui.form;


import db.entity.PlaneModel;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: PlaneModelForm.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class PlaneModelForm implements Serializable {

    private Integer planeModelId;

    private PlaneModel planeModel;


    public Integer getPlaneModelId() {
        return planeModelId;
    }

    public void setPlaneModelId(final Integer planeTypeID) {
        this.planeModelId = planeTypeID;
    }

    public PlaneModel getPlaneModel() {
        return this.planeModel;
    }

    public void setPlaneModel(final PlaneModel planeModel) {
        this.planeModel = planeModel;
    }

    public void clear() {
        planeModelId = -1;
        planeModel = null;
    }
}

