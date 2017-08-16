package ui.form;


import db.entity.Plane;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: PlaneForm.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class PlaneForm implements Serializable {

    private Integer planeId;
    private Plane plane;

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Integer getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Integer planeId) {
        this.planeId = planeId;
    }

    public void clear() {
        planeId = -1;
        plane = null;
    }

}


